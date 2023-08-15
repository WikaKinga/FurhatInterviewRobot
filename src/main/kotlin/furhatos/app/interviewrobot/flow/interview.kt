package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.language.*
import furhatos.app.interviewrobot.nlu.ChooseTopicIntent
import furhatos.app.interviewrobot.nlu.TellCVIntent
import furhatos.app.interviewrobot.nlu.TellInterviewIntent
import furhatos.app.interviewrobot.nlu.TellSkillIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val AnalyzeInterest: State = state(Interaction) {
    onEntry {
        when (users.current.topic.currentTopic!!.value) {
            "cv" -> {
                goto(AskAboutCV)
            }

            "job interview" -> {
                goto(AskAboutInterview)
            }

            "technical skills" -> {
                goto(AskAboutSkills)
            }

            else -> {
                furhat.say(topicNotFound)
                goto(RequestTopic)
            }
        }
    }
}

val RequestTopic: State = state(Interaction) {
    onEntry {
        furhat.ask(requestTopic)
    }

    onReentry {
        furhat.ask(additionalTopic)
    }

    onResponse<ChooseTopicIntent> {
        randomizeClarificationRequest()
        goto(AnalyzeInterest)
    }

    onResponse<Yes> {
        randomizeClarificationRequest()
        furhat.ask(requestTopic)
    }

    onResponse<No> {
        randomizeClarificationRequest()
        goto(End)
    }
}

val AskAboutCV: State = state(Interaction) {
    onEntry {
        furhat.ask(requestCV)
    }

    onResponse<TellCVIntent> {
        users.current.cv.adjoin(it.intent)
        randomizeClarificationRequest()
        if ( // if there is an empty slot, seek to fill it
            users.current.cv.degree == null ||
            users.current.cv.formerPositions == null ||
            users.current.cv.yrsOfExperience == null
        ) {
            reentry()
        } else {
            furhat.say("So you have ${users.current.cv}")
            goto(RequestTopic)
        }
    }

    onReentry {
        when { // if slot is empty, specifically targets slot
            users.current.cv.degree == null -> furhat.ask(requestDegree)
            users.current.cv.formerPositions == null -> furhat.ask(requestFormerPositions)
            users.current.cv.yrsOfExperience == null -> furhat.ask(requestYrsOfExperience)
        }
    }
}

val AskAboutInterview: State = state(Interaction) {
    onEntry {
        furhat.ask(requestInterviewExperience)
    }

    onResponse<TellInterviewIntent> {
        users.current.interview.adjoin(it.intent)
        randomizeClarificationRequest()
        furhat.say("${it.intent}")
        when (users.current.interview.confidence) {
            null -> reentry()
            else -> goto(RequestTopic)
        }
    }

    onReentry {
        furhat.ask(elaborate)
    }
}

val AskAboutSkills: State = state(Interaction) {
    onEntry {
        furhat.ask(requestTechnicalSkills)
    }

    onResponse<TellSkillIntent> {
        users.current.skills.adjoin(it.intent)
        randomizeClarificationRequest()
        furhat.say("${it.intent}")
        when (users.current.skills.skill) {
            null -> reentry()
            else -> goto(End)
        }
    }

    onReentry {
        furhat.ask(elaborate)
    }
}

val End: State = state(Interaction) {
    onEntry {
        furhat.say(farewell)
        dialogLogger.endSession() // stops logging for user's session
        goto(Idle)
    }
}
