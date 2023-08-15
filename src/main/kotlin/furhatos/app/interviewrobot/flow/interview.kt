package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
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
        furhat.ask("Tell me a bit about you, like your degree and your work experience. Just keep going and I will ask you for any additional information that I might need.")
    }

    onResponse<TellCVIntent> {
        users.current.cv.adjoin(it.intent)
        furhat.say("So you have ${it.intent}")
        when {
            users.current.cv.degree == null ||
                    users.current.cv.yrsOfExperience == null ||
                    users.current.cv.formerPositions == null ->
                reentry()

            else -> { furhat.say("Okay, I think I have gathered enough information about you.")
                goto(GiveCvAdvice)}
        }
    }

    onReentry {
        furhat.ask("Can you elaborate some more?")
    }
}

val GiveCvAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("What kind of cv advice do you need?")
    }
    onResponse<RequestCvAdvice> {
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

val AskIfMoreAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("Do you want more advice on this topic?")
    }
    onResponse<Yes> {
        when (users.current.topic.currentTopic!!.value) {
            "cv" -> {
                goto(GiveCvAdvice)
            }
            }
        }
    onResponse<No> {
        goto(ChooseMoreOrEnd)
    }
    }



val End: State = state(Interaction) {
    onEntry {
        furhat.say("Thanks for the conversation!")
        goto(Idle)
    }
}

val ChooseMoreOrEnd: State = state(Interaction) {
    onEntry {
        furhat.ask("Do you want to talk about another topic?")
    }
    onResponse<Yes> {
        goto(RequestTopic)
    }
    onResponse<No> {
        goto(End)
    }
}

