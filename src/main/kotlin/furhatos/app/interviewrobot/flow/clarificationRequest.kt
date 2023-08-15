package furhatos.app.interviewrobot.flow

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.partialState

val requestClarification = partialState {
    onEntry {
        furhat.ask {
            random {
                +"Sorry?"
                +"Pardon?"
                +"Pardon me?"
                +"Excuse me?"
                +"Sorry, what was that?"
                +"Sorry, could you repeat that?"
            }
        }
    }

    onResponse {
        terminate()
    }

    onExit {
        furhat.say {
            random {
                +"Oh, okay!"
                +"Ah, okay!"
                +"Uh huh"
                +"I see"
            }

            random {
                +"You were saying?"
                +"Please continue"
                +"Please keep going"
            }
        }
    }
}