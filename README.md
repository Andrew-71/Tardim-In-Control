# TARDIM: In Control ![Modrinth Downloads](https://img.shields.io/modrinth/dt/tardim-in-control?color=00AF5C&label=modrinth&style=flat&logo=modrinth)
### All of time and space, *now automated*.

![Example dashboard](https://cdn.modrinth.com/data/xsv4H3pa/images/a6726a966b6ceb6cbfa81d4886b26375ee500854.png)

This mod is an addon for the [TARDIM mod](https://www.curseforge.com/minecraft/mc-mods/tardim), and adds a way to control your time (but mostly space) machine with [ComputerCraft: Tweaked](https://tweaked.cc) computers and redstone using new blocks and peripherals.

### Features:
* Digital TARDIM interface: ComputerCraft peripheral that lets you control a TARDIM using computers! Full list of methods is available in the [Javadoc](http://andrey71.me/TARDIM-ic-docs/su/a71/tardim_ic/tardim_ic/DigitalInterfacePeripheral.html) (Sorry for  that, this is the best auto-generated docs I could find for now). The peripheral supports almost all commands that the TARDIM computer panel has.
* Redstone TARDIM Input: New block that lets you execute a TARDIM command with the power of redstone! After saving a command, this block executes it every time it gets powered by redstone
* *This is just the beginning, there are more features to come!*

### Example use-cases
* Make a dashboard to monitor fuel levels, current location, and other information on a screen in a nice way.
* Get refined control over your TARDIM, such as saving and loading locations, or setting a destination in a GUI.
* Add visual effects that activate during flight e.g. note blocks or Create mod contraptions.

The possibilities are endless, the only limit is your imagination! (And coding skills)

### Note
Due to nature of the mod **anyone** inside your TARDIM with access to a computer and this mod's peripheral
will be able to run **any** methods. There is no fix that I know of (aside from disabling any commands except "getters" like fuel info), so if you want to use this mod on your server,
please don't let untrustworthy players anywhere near your computer.\
And another thing: the method that sets destination dimension can't check if the dimension is valid. If you cannot land just change dimension to a valid one like overworld or nether.

### FAQ

**Is this for Fabric or Forge?**
: Both! As a Fabric player who recognises Forge's large playerbase, I support both major modloaders.

**Can I use this in my modpack?**
: Sure, as long as you don't claim the mod as your own. A link to this mod's page would be appreciated too.

**Will there be a 1.19.3 version and beyond?**
: Right now my focus on 1.19.2, but I will try my best to update to later versions as soon as this mod is fully stable.

**I encountered a bug or have a suggestion. What do I do?**
: If you have a problem or suggestion, the best way to get them to me is through the project's Discord server.

**I don't know ComputerCraft, will this always be a CC-only mod?**
: Ok fine, nobody actually asked that. But in case you did, good news: No! The mod already adds things like Redstone TARDIM Input, 
which let you integrate your TARDIM into good old redstone. Going forward, 
I plan to add even more ways to control the TARDIM without computers for those who don't want to code their own implementation.