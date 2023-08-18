package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.language.*
import furhatos.app.interviewrobot.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

// TOPIC HANDLER
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

    onResponse<ChooseTopicIntent> {
        randomizeClarificationRequest()
        goto(AnalyzeInterest)
    }

    onResponse<RequestTopicOptionsIntent> {
        randomizeClarificationRequest()
        furhat.ask(giveTopicOptions)
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

// TOPIC 1
val AskAboutCV: State = state(Interaction) {
    onEntry {
        furhat.ask(requestCV)
    }

    onResponse<TellCVIntent> {
        users.current.cv.adjoin(it.intent)
        randomizeClarificationRequest()
        goto(CheckCvProfile)
        }
    }


val CheckCvProfile : State = state(Interaction) {
    onEntry {
        when { // if slot is empty, specifically targets slot
            users.current.cv.degree == null -> goto(RequestDegree)
            users.current.cv.formerPositions == null -> goto(RequestPositions)
            users.current.cv.yrsOfExperience == null -> goto(RequestExperience)
            else -> {
                furhat.say("So you have ${users.current.cv}")
                goto(GiveCVAdvice)
            }
        }
    }
}

val RequestDegree: State = state(Interaction){
    onEntry {
        furhat.ask(requestDegree)
    }
    onResponse<TellDegreeIntent> {
        users.current.cv.degree = it.intent.degree
        randomizeClarificationRequest()
    }
}

val RequestExperience: State = state(Interaction){
    onEntry {
        furhat.ask(requestFormerPositions)
    }
    onResponse<TellExperienceIntent> {
        users.current.cv.yrsOfExperience = it.intent.yrsOfExperience
        randomizeClarificationRequest()
    }
}

val RequestPositions: State = state(Interaction){
    onEntry {
        furhat.ask(requestFormerPositions)
    }
    onResponse<TellPositionsIntent> {
        users.current.cv.yrsOfExperience = it.intent.formerPositions
        randomizeClarificationRequest()
    }
}

val GiveCVAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("What kind of cv advice do you need?")
    }

    onResponse<RequestCVAdvice> {
        users.current.cvAdviceNeed.adjoin(it.intent)
        when (users.current.cvAdviceNeed.cvAdviceNeed!!.value) {
            "contents" -> furhat.say("Here is my advice on content.")
            "cv with no experience" -> furhat.say("Here is my advice for a cv for the first job.")
            "structure" -> furhat.say("Here's how you should structure a cv.")
            "personal interests" -> furhat.say("Here's some kinds of personal interests you can include.")
        }

        goto(AskIfMoreAdvice)
    }
}

// TOPIC 2
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
            else -> goto(GiveInterviewAdvice)
        }
    }

    onReentry {
        furhat.ask(elaborate)
    }
}

val GiveInterviewAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("What kind of interview advice do you need?")
    }

    onResponse<RequestInterviewAdvice> {
        users.current.interviewAdviceNeed.adjoin(it.intent)
        when (users.current.interviewAdviceNeed.interviewAdviceNeed!!.value) {
            "nervous" -> furhat.say("Here is my advice on nervousness.")
            "preparation" -> furhat.say("Here is my advice on preparation.")
            "clothes" -> furhat.say("Here is my advice on clothes.")
            "questions" -> furhat.say("Here is my advice on questions.")
        }

        goto(AskIfMoreAdvice)
    }
}

// TOPIC 3
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
            else -> goto(GiveSkillsAdvice)
        }
    }

    onReentry {
        furhat.ask(elaborate)
    }
}

val GiveSkillsAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("What kind of technical skill advice do you need?")
    }

    onResponse<RequestSkillsAdvice> {
        users.current.skillsAdviceNeed.adjoin(it.intent)
        when (users.current.skillsAdviceNeed.skillsAdviceNeed!!.value) {
            "format" -> furhat.say("Here is my advice on format.")
            "language" -> furhat.say("Here is my advice on languages.")
        }

        goto(AskIfMoreAdvice)
    }
}

// ADVICE HANDLER
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

// NEW TOPIC HANDLER
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

// USER SESSION END STATE
val End: State = state(Interaction) {
    onEntry {
        furhat.say(farewell)
        dialogLogger.endSession() // stops logging for user's session
        goto(Idle)
    }
}
