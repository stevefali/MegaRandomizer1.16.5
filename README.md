# Mega Randomizer 1.16.5
This is the 1.16.5 version of my [Mega Randomizer](https://github.com/stevefali/MegaRandomizer) Minecraft mod; I made it at the request of users on [Forge](https://www.curseforge.com/minecraft/mc-mods/mega-randomizer).
I had a lot of fun building this project, and I must admit, it was more work than I expected.
At a high level, the functionality of the mod is achieved in a similar way to the original version of Mega Randomizer. The bulk of the work on this project was learning how
Minecraft and Forge's code worked in 1.16.5 and making changes accordingly.

### The repositories for the other versions of Mega Randomizer can be found here:
- [Main version](https://github.com/stevefali/MegaRandomizer)
- [1.7.10](https://github.com/stevefali/MegaRandomizer1.7.10)

## Here is a high-level overview of how the code works
- There are 3 new custom game rules, doBlockRandomDrops, doEntityRandomDrops, and doPlayerRandomDrops. These can be turned on or off using commands, or using the custom GUI menu.
- There is a custom menu screen with buttons for turning the drop randomization types on or off.
    - The event for the in-game menu (the pause screen) is intercepted so that I can instead show my customized version of the pause screen that includes a button for the Mega Randomizer menu screen.
    - The Mega Randomizer options screen runs only on the client, so network packets are used to communicate with the server to update the custom game rules.
    - When the Mega Randomizer options screen is called, it also sends a request to the server to update the client with the current state of the 3 custom game rules.
- Entity drop and player drop randomization is achieved by listening for an entity drop event and using the event object to replace the vanilla drops with the randomized ones.
- Block drop randomization is achieved by:
    - Creating a custom loot condition that checks if the drops are from a block.
    - Creating a custom loot modifier that uses a loot modifier JSON file to tell the system to check for the custom loot condition and then replaces the vanilla drops with randomized ones.
- The actual randomization of all drops is achieved by:
    - Getting a copy of the list of all registered items at runtime. This allows the mod to include randomization of drop items that are added by other Forge mods that may be running.
    - Removing from that list the items that I don't want to be obtainable as drops (spawn eggs, debug stick, command blocks, etc.)
    - Shuffling the list using the game seed to produce a randomly-ordered list that is unique to that world.
 
## Help Support Mega Randomizer
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/H2H5103UQ6)
