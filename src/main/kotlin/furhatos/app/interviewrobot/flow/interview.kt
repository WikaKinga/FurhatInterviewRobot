package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.nlu.ChooseTopicIntent
import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.nlu.TellCvIntent
import furhatos.app.interviewrobot.nlu.TellInterviewIntent
import furhatos.app.interviewrobot.nlu.TellSkillIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*

val AnalyzeInterest: State = state(Interaction) {
    onEntry {
        val topic = users.current.topic.currentTopic!!.value
        when (topic) {
            "cv" -> {
                goto(AskAboutCv)
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

val RequestTopic: State = state(Interaction){
    onEntry {
        furhat.ask("What topic are you interested in talking about?")
    }

    onResponse<ChooseTopicIntent> {
        goto(AnalyzeInterest)
    }
}

val AskAboutCv: State = state(Interaction) {
    onEntry {
        furhat.ask("Tell me about your experience with writing cv's")
    }
    onResponse<TellCvIntent> {
        users.current.profile.adjoin(it.intent)
    }
}

val AskAboutInterview: State = state(Interaction) {
    onEntry {
        furhat.ask("Tell me about your experience with job interviews")
    }
    onResponse<TellInterviewIntent> {
        users.current.profile.adjoin(it.intent)
    }
}

val AskAboutSkills: State = state(Interaction) {
    onEntry {
        furhat.ask("Tell me about your technical skills")
    }
    onResponse<TellSkillIntent> {
        users.current.profile.adjoin(it.intent)
    }
}