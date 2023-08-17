- ~~Let furhat answer anything when slot is filled(DONE), more slots per topic, loop conversation~~ DONE


- Define **more** slots/content for the user profile, especially for the skills and job interview topics
  - for every slot there must be a Request state, where furhat requests info for that slot and a Tell intent where the user tells that slot info
  - Request state only entered when user does not give info unprompted
  - For everything that can be used to fill a slot an entity must be created ore build-in entity used
  - Slot filling should feel as natural as possible, user should give information any way they want, furhat should not ask a lot of specific questions regarding any slots
    - Maybe can prompt the user to say a little more when too few slot have been filled without being too specific. Ideally the user will not notice there are slots to fill

  

  

- Clarification requests


- ~~Make change of topic possible at every state~~ DONE


- Make things like Skill in the user profile a list of things so that furhat can advise on learning new things if list is too short
  - Maybe here use a llm? when user chooses a new skill they are interested in, some advice on how to best acquire it can be given?


- ~~Implement logging~~DONE


- Implement a give advice state, only entered if a certain amount of profile slots for a given topic have been filled. As long as not enough slots have been filled, reentry AskAboutX state
  - How to write a function that checks how many slots in the profile have been filled? Function must access users.kt file as var get reset to null on reentry of state. ~~Maybe in users.kt add different vars for each topic and not one for the whole profile? Maybe would make access easier...~~ DONE THAT PART 


- Other:
  - Consent form
  - Dialogue Evaluation Form