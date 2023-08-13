package furhatos.app.interviewrobot

import furhatos.app.interviewrobot.nlu.ChooseTopicIntent
import furhatos.app.interviewrobot.nlu.TellCVIntent
import furhatos.app.interviewrobot.nlu.TellInterviewIntent
import furhatos.app.interviewrobot.nlu.TellSkillIntent
import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.records.User

//Current topic user wants to talk about
val User.topic by NullSafeUserDataDelegate {
    ChooseTopicIntent()
}

//User profile

val User.cv by NullSafeUserDataDelegate {
    TellCVIntent()
}

val User.interview by NullSafeUserDataDelegate {
    TellInterviewIntent()
}

val User.skills by NullSafeUserDataDelegate {
    TellSkillIntent()
}