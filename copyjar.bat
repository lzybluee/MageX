mkdir mage-client\lib
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-1.4.29.jar	mage-client\lib\mage-1.4.29.jar
copy ..\MageX\Mage.Client\target\mage-client.jar	mage-client\lib\mage-client-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-common-1.4.29.jar	mage-client\lib\mage-common-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-deck-constructed-1.4.29.jar	mage-client\lib\mage-deck-constructed-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-sets-1.4.29.jar	mage-client\lib\mage-sets-1.4.29.jar

mkdir mage-server\lib
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-1.4.29.jar	mage-server\lib\mage-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-common-1.4.29.jar	mage-server\lib\mage-common-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-canadianhighlanderduel-1.4.29.jar	mage-server\lib\mage-game-canadianhighlanderduel-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-commanderfreeforall-1.4.29.jar	mage-server\lib\mage-game-commanderfreeforall-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-freeformcommanderfreeforall-1.4.29.jar	mage-server\lib\mage-game-freeformcommanderfreeforall-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-momirduel-1.4.29.jar	mage-server\lib\mage-game-momirduel-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-momirfreeforall-1.4.29.jar	mage-server\lib\mage-game-momirfreeforall-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-pennydreadfulcommanderfreeforall-1.4.29.jar	mage-server\lib\mage-game-pennydreadfulcommanderfreeforall-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-tinyleadersduel-1.4.29.jar	mage-server\lib\mage-game-tinyleadersduel-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-player-ai-1.4.29.jar	mage-server\lib\mage-player-ai-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-server-1.4.29.jar	mage-server\lib\mage-server-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-sets-1.4.29.jar	mage-server\lib\mage-sets-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-tournament-constructed-1.4.29.jar	mage-server\lib\mage-tournament-constructed-1.4.29.jar

mkdir mage-server\plugins
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-deck-constructed-1.4.29.jar	mage-server\plugins\mage-deck-constructed-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-deck-limited-1.4.29.jar	mage-server\plugins\mage-deck-limited-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-commanderduel-1.4.29.jar	mage-server\plugins\mage-game-commanderduel-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-freeforall-1.4.29.jar	mage-server\plugins\mage-game-freeforall-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-twoplayerduel-1.4.29.jar	mage-server\plugins\mage-game-twoplayerduel-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-player-ai-draftbot-1.4.29.jar	mage-server\plugins\mage-player-ai-draftbot-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-player-ai-ma-1.4.29.jar	mage-server\plugins\mage-player-ai-ma-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-player-aiminimax-1.4.29.jar	mage-server\plugins\mage-player-aiminimax-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-player-human-1.4.29.jar	mage-server\plugins\mage-player-human-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-tournament-boosterdraft-1.4.29.jar	mage-server\plugins\mage-tournament-boosterdraft-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-tournament-sealed-1.4.29.jar	mage-server\plugins\mage-tournament-sealed-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-brawlduel-1.4.29.jar	mage-server\plugins\mage-game-brawlduel-1.4.29.jar
copy ..\MageX\Mage.Stats\target\mage-stats-ws\WEB-INF\lib\mage-game-brawlfreeforall-1.4.29.jar	mage-server\plugins\mage-game-brawlfreeforall-1.4.29.jar


pause
