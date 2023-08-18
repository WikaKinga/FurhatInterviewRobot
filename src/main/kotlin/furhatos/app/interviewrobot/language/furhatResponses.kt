package furhatos.app.interviewrobot.language

import furhatos.app.interviewrobot.nlu.Topic
import furhatos.flow.kotlin.utterance
import furhatos.gestures.Gestures
import furhatos.util.Language

val greet = utterance {
    +Gestures.BigSmile
    +"Hey there, how can I help you?"
}

val topicNotFound = utterance {
    +Gestures.BrowFrown
    +"No suitable topic given."
}

val giveTopicOptions = utterance {
    +"I would be happy to talk with you about ${Topic().getEnum(Language.ENGLISH_US).joinToString(", ")}"
    +"Do any of those topics interest you?"
}

val requestTopic = utterance {
    random {
        +"What would you like to talk about?"
        +"What topic are you interested in talking about?"
        +"What should we talk about?"
    }
}

val requestCV = utterance {
    +"Tell me a bit about yourself."
    +"For example, your degree, how many positions you've held, and how many years of experience you have."
}

val requestDegree = utterance {
    +"How many years of work experience do you have?"
}

val requestFormerPositions = utterance {
    +"How many positions have you held?"
}

val requestYrsOfExperience = utterance {
    +"How many years of experience do you have in your field? ?"
}

val askCvQuestion = utterance {
    +"What are your concerns when it comes to writing a CV?"
    +"How many CV's have you written so far?"
}

val giveCvContentAdvice = utterance {
    +"A well crafted CV should at least contain the following key sections: 1. Your contact information, 2. Information about your education and degrees. 3. Your work experience. 4. Your technical skills and your soft skills. 5. your projects, achievements and awards. You can also add some personal interests if you'd like."
}

val requestInterviewExperience = utterance {
    +"Tell me about your experience with job interviews and what exactly you need help with."
}

val askInterviewQuestion = utterance {
    +"Tell me about your last job interview"
    +"Tell me about your worst job interview experience"
    +"Tell me about your best job interview experience"
}

val requestTechnicalSkills = utterance {
    +"Tell me about your technical skills."
    +"For example, are you proficient in any programming languages?"
}

val elaborate = utterance {
    +"Can you elaborate some more on that?"
}

val additionalTopic = utterance {
    random {
        +"Would you like to talk about anything else?"
        +"Would you care to talk about something else?"
        +"Are there any other topics you'd like to discuss?"
    }
}

val farewell = utterance {
    random {
        +"Thank you for the conversation!"
        +"It was nice talking to you. Have a nice day!"
        +"It was nice talking. Good luck on your interview!"
    }
}