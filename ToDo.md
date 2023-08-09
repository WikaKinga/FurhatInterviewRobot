- **1st Priority: Let furhat answer anything when slot is filled, more slots per topic, loop conversation**


- Define all slots for the user profile
  - for every slot there must be a Request state, where furhat requests info for that slot and a Tell intent where the user tells that slot info
  - Request state only entered when user does not give info unprompted
  - For everything that can be used to fill a slot an entity must be created
  - Slot filling should feel as natural as possible, user should give information any way they want, furhat should not ask a lot of specific questions regarding any slots
    - Maybe can promt the user to say a little more when too few slot have been filled without being too specific. Ideally the user will not notice there are slots to fill


- Is there another way for furhat to keep track of what's been said other than slots in the user profile? I don't think so ...


- Is there a way to make every topic its own entity? Is this necessary?


- Clarification requests


- Make change of topic possible at every state


- Make things like Skill in the user profile a list of things so that furhat can advise on learning new things if list is too short
  - Maybe here use a llm? when user chooses a new skill they are interested in, some advice on how to best acquire it can be given?


- Implement logging
