ATTENTION CONTRIBUTORS:
This mod has a separate repository for its resources folder. This is to make developing the Forge and Fabric versions more convenient.
When cloning this repository, add --recurse-submodules to your clone command, this will fetch the resources.
If it proves unwieldy, check out https://git-scm.com/book/en/v2/Git-Tools-Submodules as it covers the implementation.
FOR TRANSLATORS WHO DON'T WANT TO DEAL WITH THAT:
You can make a PR here and it will apply to both the forge and fabric versions:
https://github.com/jsburger/ap-assets



For any devs that are here because my mod is being weird;
- Warping recipes are in core/crafting, and is mechanically handled by common/event/ChangeDimensionHandler and a mixin in core.mixin
- Access transforming is only for setting flammability
- Withered bone drop handling is in core/loot, the item tag is just for mass replacing in recipes
- Trades are listed in APTrades in core/integration, handled in common/event/TradingEventHandler