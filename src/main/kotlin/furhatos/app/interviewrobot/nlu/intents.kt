package furhatos.app.interviewrobot.nlu

import furhatos.nlu.TextGenerator
import furhatos.util.Language
import furhatos.nlu.*
import furhatos.nlu.common.*
import furhatos.nlu.common.Number
import furhatos.records.GenericRecord

class ChooseTopicIntent : Intent() {
    var currentTopic : Topic? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I would like to talk about @currentTopic",
            "I'd like to know more about @currentTopic",
            "Can you help me with @currentTopic",
            "I would like to have some advice on @currentTopic"
        )
    }
}

class TellCvIntent : Intent(), TextGenerator {
    var degree : Degree? = null
    var formerEmployments : Number? = null
    var yrsOfExperience : Number? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I have a @degree",
            "I have worked for @formerEmployments companies",
            "I have @yrsOfExperience years of experience in that field",
            "I have a @degree and have worked for @formerEmployments companies"

        )
    }
    override fun toText(lang: Language) : String {
        return generate(lang, "[a $degree][$formerEmployments former employments][$yrsOfExperience years of working experience]")
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