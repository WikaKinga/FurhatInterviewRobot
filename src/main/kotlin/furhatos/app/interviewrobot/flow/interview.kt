package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.language.*
import furhatos.app.interviewrobot.nlu.*
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
            goto(GiveCVAdvice)
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

val GiveCVAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("What kind of cv advice do you need?")
    }
    onResponse<RequestCVAdvice> {
        users.current.cvAdviceNeed.adjoin(it.intent)
        when (users.current.cvAdviceNeed.adviceNeed!!.value) {
            "contents" -> furhat.say("Here is my advice on content.")
            "cv with no experience" -> furhat.say("Here is my advice for a cv for the first job.")
            "structure" -> furhat.say("Here's how you should structure a cv.")
            "personal interests" -> furhat.say("Here's some kinds of personal interests you can include.")
        }
        goto(AskIfMoreAdvice)
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

val AskIfMoreAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("Do you want more advice on this topic?")
    }

    onResponse<Yes> {
        when (users.current.topic.currentTopic!!.value) {
            "cv" -> {
                goto(GiveCVAdvice)
            }
        }
    }

    onResponse<No> {
        goto(ChooseMoreOrEnd)
    }
}

val ChooseMoreOrEnd: State = state(Interaction) {
    onEntry {
        furhat.ask(additionalTopic)
    }

    onResponse<Yes> {
        goto(RequestTopic)
    }

    onResponse<No> {
        goto(End)
    }
}

val End: State = state(Interaction) {
    onEntry {
        furhat.say(farewell)
        dialogLogger.endSession() // stops logging for user's session
        goto(Idle)
    }
}
