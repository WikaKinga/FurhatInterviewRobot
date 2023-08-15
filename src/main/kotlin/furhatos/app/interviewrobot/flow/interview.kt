package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.nlu.ChooseTopicIntent
import furhatos.app.interviewrobot.nlu.TellCVIntent
import furhatos.app.interviewrobot.nlu.TellInterviewIntent
import furhatos.app.interviewrobot.nlu.TellSkillIntent
import furhatos.flow.kotlin.*

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
                furhat.say("No suitable topic given.")
                goto(RequestTopic)
            }
        }
    }
}

val RequestTopic: State = state(Interaction) {
    onEntry {
        furhat.ask("What topic are you interested in talking about?")
    }

    onResponse<ChooseTopicIntent> {
        goto(AnalyzeInterest)
    }
}

val AskAboutCV: State = state(Interaction) {
    onEntry {
        furhat.ask("Tell me a bit about you, like your degree, your work experience and what exactly you need help with.")
    }

    onResponse<TellCVIntent> {
        users.current.cv.adjoin(it.intent)
        furhat.say("So you have ${it.intent}")
        when {
            users.current.cv.degree == null ||
                    users.current.cv.yrsOfExperience == null ||
                    users.current.cv.formerPositions == null ->
                reentry()

            else -> goto(End)
        }
    }

    onReentry {
        furhat.ask("Can you elaborate some more?")
    }
}

val AskAboutInterview: State = state(Interaction) {
    onEntry {
        furhat.ask("Tell me about your experience with job interviews and what exactly you need help with.")
    }

    onResponse<TellInterviewIntent> {
        users.current.interview.adjoin(it.intent)
        furhat.say("${it.intent}")
        when (users.current.interview.confidence) {
            null -> reentry()
            else -> goto(End)
        }
    }

    onReentry {
        furhat.ask("Can you elaborate some more?")
    }
}

val AskAboutSkills: State = state(Interaction) {
    onEntry {
        furhat.ask("Tell me about your technical skills")
    }

    onResponse<TellSkillIntent> {
        users.current.skills.adjoin(it.intent)
        furhat.say("${it.intent}")
        when (users.current.skills.skill) {
            null -> reentry()
            else -> goto(End)
        }
    }

    onReentry {
        furhat.ask("Can you elaborate some more?")
    }
}

val End: State = state(Interaction) {
    onEntry {
        furhat.say("Thanks for the conversation!")
        dialogLogger.endSession()
        goto(Idle)
    }
}
