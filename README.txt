Yo whats up, jsburg here. I put most of the mod together, 95% we'll say.
As it would happen, this is also my first full mod. If you're reading this you're probably on the repository.
Feel free to pick apart the code in here. If there's something off, please let me know. I don't know any better.

And for any devs that are here because my mod is being weird;
- Warping recipes are in core/crafting, and is mechanically handled by common/event/ChangeDimensionHandler
- Access transforming is only for setting flammability and getting access to the block stripping map
- The mixin is on ItemEntitys and is hooking into them changing dimension
- Withered bone drop handling is in core/loot, and yes, the item tag is just for mass replacing in recipes
- Trades are listed in APTrades in core/integration, handled in common/event/TradingEventHandler