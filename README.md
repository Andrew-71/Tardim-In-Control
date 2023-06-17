![TARDIM: IC Banner](./media/banner.png)
### All of time and space, *now automated and improved*.

This mod is an addon for the [TARDIM mod](https://www.curseforge.com/minecraft/mc-mods/tardim), 
and adds a way to control your time (but mostly space) machine with new blocks, redstone, 
and even other mods like [ComputerCraft: Tweaked](https://tweaked.cc) computers or [Create](https://github.com/Creators-of-Create/Create)

### Features:
TODO: Re-make this for 1.2
* Digital TARDIM interface: ComputerCraft peripheral that lets you control a TARDIM using computers! Full list of methods is available in the [Javadoc](http://andrey71.me/TARDIM-ic-docs/su/a71/tardim_ic/tardim_ic/DigitalInterfacePeripheral.html) (Sorry for  that, this is the best auto-generated docs I could find for now). The peripheral supports almost all commands that the TARDIM computer panel has.
* Redstone TARDIM Input: New block that lets you execute a TARDIM command with the power of redstone! After saving a command, this block executes it every time it gets powered by redstone
* *This is just the beginning, there are more features to come!*

### Example use-cases
* Make a dashboard to monitor fuel levels, current location, and other information on a screen in a nice way.
* Get refined control over your TARDIM, such as saving and loading locations, or setting a destination in a GUI.
* Add visual effects that activate during flight e.g. note blocks or Create mod contraptions.

The possibilities are endless, the only limit is your imagination!

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

**Will there be a 1.20 version and beyond?**
: Right now my focus on 1.19.2, but I will try my best to move to later versions as soon as this mod is fully stable,
and dependencies are out.

**I encountered a bug or have a suggestion. What do I do?**
: If you have a problem or suggestion, the best way to get them to me is through the project's Discord server.

**Do I need to install ComputerCraft or Create for this mod?**
: Nope! While I highly suggest you do (because digital interface is in my opinion the star of the show), 
you can enjoy a lot of TARDIM: In Control's features with only TARDIM installed. 
If you ever decide to add other optional dependencies, the relevant blocks will become available!