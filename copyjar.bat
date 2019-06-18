mkdir mage-client\lib
copy ..\MageX\Mage\target\mage.jar	mage-client\lib\mage-1.4.36.jar
copy ..\MageX\Mage.Client\target\mage-client.jar	mage-client\lib\mage-client-1.4.36.jar
copy ..\MageX\Mage.Common\target\mage-common.jar	mage-client\lib\mage-common-1.4.36.jar
copy ..\MageX\Mage.Sets\target\mage-sets.jar	mage-client\lib\mage-sets-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Deck.Constructed\target\mage-deck-constructed.jar	mage-client\lib\mage-deck-constructed-1.4.36.jar

mkdir mage-server\lib
copy ..\MageX\Mage\target\mage.jar	mage-server\lib\mage-1.4.36.jar
copy ..\MageX\Mage.Server\target\mage-server.jar	mage-server\lib\mage-server-1.4.36.jar
copy ..\MageX\Mage.Common\target\mage-common.jar	mage-server\lib\mage-common-1.4.36.jar
copy ..\MageX\Mage.Sets\target\mage-sets.jar	mage-server\lib\mage-sets-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Player.AI\target\mage-player-ai.jar	mage-server\lib\mage-player-ai-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Tournament.Constructed\target\mage-tournament-constructed.jar	mage-server\lib\mage-tournament-constructed-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.BrawlDuel\target\mage-game-brawlduel.jar	mage-server\lib\mage-game-brawlduel-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.BrawlFreeForAll\target\mage-game-brawlfreeforall.jar	mage-server\lib\mage-game-brawlfreeforall-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.CanadianHighlanderDuel\target\mage-game-canadianhighlanderduel.jar	mage-server\lib\mage-game-canadianhighlanderduel-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.CommanderFreeForAll\target\mage-game-freeforall.jar	mage-server\lib\mage-game-commanderfreeforall-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.FreeformCommanderDuel\target\mage-game-freeformcommanderduel.jar	mage-server\lib\mage-game-freeformcommanderduel-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.FreeformCommanderFreeForAll\target\mage-game-freeforall.jar	mage-server\lib\mage-game-freeformcommanderfreeforall-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.MomirDuel\target\mage-game-momirduel.jar	mage-server\lib\mage-game-momirduel-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.MomirGame\target\mage-game-momir.jar	mage-server\lib\mage-game-momirfreeforall-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.OathbreakerDuel\target\mage-game-oathbreakerduel.jar	mage-server\lib\mage-game-oathbreakerduel-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.OathbreakerFreeForAll\target\mage-game-freeforall.jar	mage-server\lib\mage-game-oathbreakerfreeforall-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.PennyDreadfulCommanderFreeForAll\target\mage-game-freeforall.jar	mage-server\lib\mage-game-pennydreadfulcommanderfreeforall-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.TinyLeadersDuel\target\mage-game-tinyleadersduel.jar	mage-server\lib\mage-game-tinyleadersduel-1.4.36.jar

mkdir mage-server\plugins
copy ..\MageX\Mage.Server.Plugins\Mage.Deck.Constructed\target\mage-deck-constructed.jar	mage-server\plugins\mage-deck-constructed-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Deck.Limited\target\mage-deck-limited.jar	mage-server\plugins\mage-deck-limited-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.CommanderDuel\target\mage-game-commanderduel.jar	mage-server\plugins\mage-game-commanderduel-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.FreeForAll\target\mage-game-freeforall.jar	mage-server\plugins\mage-game-freeforall-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Game.TwoPlayerDuel\target\mage-game-twoplayerduel.jar	mage-server\plugins\mage-game-twoplayerduel-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Player.AI.DraftBot\target\mage-player-ai-draft-bot.jar	mage-server\plugins\mage-player-ai-draftbot-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Player.AI.MA\target\mage-player-ai-ma.jar	mage-server\plugins\mage-player-ai-ma-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Player.AIMinimax\target\mage-player-aiminimax.jar	mage-server\plugins\mage-player-aiminimax-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Player.Human\target\mage-player-human.jar	mage-server\plugins\mage-player-human-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Tournament.BoosterDraft\target\mage-tournament-booster-draft.jar	mage-server\plugins\mage-tournament-boosterdraft-1.4.36.jar
copy ..\MageX\Mage.Server.Plugins\Mage.Tournament.Sealed\target\mage-tournament-sealed.jar	mage-server\plugins\mage-tournament-sealed-1.4.36.jar

pause
