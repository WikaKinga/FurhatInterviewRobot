package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.nlu.ChooseTopicIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No

val Greeting: State = state(Interaction) {
    onEntry {
        furhat.ask("Hey there, how can I help you?")
    }

    onResponse<ChooseTopicIntent> {
        furhat.say("Alright!")
        users.current.topic.adjoin(it.intent)
        goto(AnalyzeInterest)
    }

    onResponse<No> {
        furhat.say("Ok.")
    }

}

