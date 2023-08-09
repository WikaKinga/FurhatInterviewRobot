package furhatos.app.interviewrobot.nlu

import furhatos.nlu.EnumEntity
import furhatos.util.Language

class Topic : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("cv", "job interview", "job interviews", "technical skills")
    }
}

class Degree : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Bachelor's", "Master's", "Doctorate", "Associates")
    }
}

class Skill : EnumEntity(speechRecPhrases = true) {

    override fun getEnum(lang: Language): List<String> {
        return listOf("Python", "Java", "HTML")
    }
}

class InterviewConfidence : EnumEntity(speechRecPhrases = true){
    override fun getEnum(lang: Language): List<String> {
        return listOf("confident", "not confident")
    }
}