package furhatos.app.interviewrobot.nlu

import furhatos.nlu.TextGenerator
import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.util.Language

class ChooseTopicIntent : Intent() {
    var currentTopic: Topic? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I would like to talk about @currentTopic",
            "I want to talk about @currentTopic",
            "I'd like to know more about @currentTopic",
            "Can you help me with @currentTopic",
            "I would like to have some advice on @currentTopic"
        )
    }
}

open class TellCVIntent : Intent(), TextGenerator {
    var degree: Degree? = null
    var formerPositions: Number? = null
    var yrsOfExperience: Number? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@degree",
            "@formerPositions",
            "@yrsOfExperience",
            "I have a @degree",
            "I have a @degree degree",
            "I have had @formerPositions jobs",
            "I have held @formerPositions positions",
            "I have worked for @formerPositions companies",
            "I have worked in @formerPositions companies",
            "I have worked for @yrsOfExperience years",
            "I have @yrsOfExperience years of experience",
            "I have @yrsOfExperience years of work experience",
            "I have @yrsOfExperience years of experience in that field",
            "I have a @degree and have worked for @formerPositions companies",
            "I have a @degree and have worked for @yrsOfExperience years",
            "I have worked for @formerPositions companies over @yrsOfExperience years",
            "I have worked for @yrsOfExperience years in @formerPositions companies",
            "I have worked for @yrsOfExperience years in @formerPositions positions",
            "I have a @degree, have worked for @yrsOfExperience years, and have had @formerPositions jobs",
            "I have a @degree, have worked for @yrsOfExperience years, and have held @formerPositions positions",
            "I have worked for @yrsOfExperience years in @formerPositions companies and have a @degree"
        )
    }

    override fun toText(lang: Language): String {
        return generate(
            lang,
            "[a $degree][$formerPositions former positions][$yrsOfExperience years of working experience]"
        )
    }

    override fun toString(): String {
        return toText()
    }
}

class TellInterviewIntent : Intent(), TextGenerator {
    var confidence: InterviewConfidence? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I am @confidence"
        )
    }

    override fun toText(lang: Language): String {
        return generate(lang, "[I see, you are $confidence during job interviews.]")
    }

    override fun toString(): String {
        return toText()
    }
}

class TellSkillIntent : Intent(), TextGenerator {
    var skill: Skill? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I know @skill",
            "I am proficient in @skill",
            "I have experience with @skill"
        )
    }

    override fun toText(lang: Language): String {
        return generate(lang, "[So you are proficient in $skill]")
    }

    override fun toString(): String {
        return toText()
    }
}

class RequestCvAdvice : Intent(){
    var adviceNeed: CvAdviceNeed? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf("I need advice on @adviceNeed", "@adviceNeed")
    }
}