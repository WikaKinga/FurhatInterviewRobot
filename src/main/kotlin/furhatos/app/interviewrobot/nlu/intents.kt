package furhatos.app.interviewrobot.nlu

import furhatos.util.Language
import furhatos.nlu.*
import furhatos.nlu.common.*

class ChooseTopicIntent : Intent() {
    var currentTopic : Topic? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I would like to talk about @currentTopic",
            "I'd like to know more about @currentTopic",
            "Can you help me with @currentTopic"
        )
    }
}

class TellCvIntent : Intent() {
    var degree : Degree? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I have a @degree"
        )
    }
}

class TellInterviewIntent : Intent() {
    var confidence : InterviewConfidence? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I am @confidence",
            "I am @confidence"
        )
    }
}

class TellSkillIntent : Intent() {
    var skill : Skill? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I know @skill")
    }
}