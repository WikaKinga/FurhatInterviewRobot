package furhatos.app.interviewrobot.nlu

import furhatos.nlu.TextGenerator
import furhatos.util.Language
import furhatos.nlu.*
import furhatos.nlu.common.*
import furhatos.records.GenericRecord

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

class TellCvIntent : Intent(), TextGenerator {
    var degree : Degree? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I have a @degree"
        )
    }
    override fun toText(lang: Language) : String {
        return generate(lang, "[So you have a $degree]")
    }

    override fun toString(): String {
        return toText()
    }
}

class TellInterviewIntent : Intent(), TextGenerator {
    var confidence : InterviewConfidence? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I am @confidence",
            "I am @confidence"
        )
    }
    override fun toText(lang: Language) : String {
        return generate(lang, "[I see, you are $confidence during job interviews.]")
    }
    override fun toString(): String {
        return toText()
    }
}

class TellSkillIntent : Intent(), TextGenerator {
    var skill : Skill? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I know @skill")
    }
    override fun toText(lang: Language) : String {
        return generate(lang, "[So you are proficient in $skill]")
    }
    override fun toString(): String {
        return toText()
    }
}