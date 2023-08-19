package furhatos.app.interviewrobot.nlu

import furhatos.nlu.EnumEntity
import furhatos.util.Language

class Topic : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("cv", "job interview", "job interviews", "interviews")
    }
}

class Degree : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Associates", "Bachelors", "Masters", "Doctorate", "Ph.D.")
    }
}

class Skill : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Python", "Java", "HTML")
    }
}

class InterviewConfidence : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("confident", "not confident")
    }
}

class CvAdviceNeed : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("contents:cv contents,contents of a cv,what to put in a cv,how to write a cv",
            "cv with no experience:how to write a cv if I have no experience,how to write a cv for the first job",
            "structure:how to structure a cv,what's a good structure for a cv, structure",
            "personal interests:what are good personal interest to mention in a cv, should I mention personal interests")
    }
}