/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.player.ai;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.turn.BeginCombatStep;
import mage.game.turn.CleanupStep;
import mage.game.turn.CombatDamageStep;
import mage.game.turn.CombatPhase;
import mage.game.turn.DeclareAttackersStep;
import mage.game.turn.DeclareBlockersStep;
import mage.game.turn.EndOfCombatStep;
import mage.game.turn.EndPhase;
import mage.game.turn.EndStep;
import mage.game.turn.FirstCombatDamageStep;
import mage.game.turn.PostCombatMainPhase;
import mage.game.turn.PostCombatMainStep;
import mage.game.turn.Step;
import org.apache.log4j.Logger;

/**
 *
 * @author ayratn
 */
public class ComputerPlayer7 extends ComputerPlayer6 {

    private static final Logger logger = Logger.getLogger(ComputerPlayer7.class);

    private boolean allowBadMoves;

    public ComputerPlayer7(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
    }

    public ComputerPlayer7(final ComputerPlayer7 player) {
        super(player);
        this.allowBadMoves = player.allowBadMoves;
    }

    @Override
    public ComputerPlayer7 copy() {
        return new ComputerPlayer7(this);
    }

    @Override
    public boolean priority(Game game) {
        game.resumeTimer(getTurnControlledBy());
        boolean result = priorityPlay(game);
        game.pauseTimer(getTurnControlledBy());
        return result;
    }

    private boolean priorityPlay(Game game) {
        if (lastLoggedTurn != game.getTurnNum()) {
            lastLoggedTurn = game.getTurnNum();
            logger.info("======================= Turn: " + game.getTurnNum() + " [" + game.getPlayer(game.getActivePlayerId()).getName() + "] =========================================");
        }
        logState(game);
        logger.debug("Priority -- Step: " + (game.getTurn().getStepType() + "                       ").substring(0, 25) + " ActivePlayer-" + game.getPlayer(game.getActivePlayerId()).getName() + " PriorityPlayer-" + name);
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);
        switch (game.getTurn().getStepType()) {
            case UPKEEP:
            case DRAW:
                pass(game);
                return false;
            case PRECOMBAT_MAIN:
                if (game.getActivePlayerId().equals(playerId)) {
                    printOutState(game);
                    if (actions.isEmpty()) {
                        logger.info("Sim Calculate pre combat actions ----------------------------------------------------- ");
                        calculatePreCombatActions(game);
                    }
                    act(game);
                    return true;
                } else {
                    pass(game);
                }
                return false;
            case BEGIN_COMBAT:
                pass(game);
                return false;
            case DECLARE_ATTACKERS:
                if (!game.getActivePlayerId().equals(playerId)) {
                    printOutState(game);
                    if (actions.isEmpty()) {
                        logger.info("Sim Calculate declare attackers actions ----------------------------------------------------- ");
                        calculatePreCombatActions(game);
                    }
                    act(game);
                    return true;
                } else {
                    pass(game);
                }
                return false;
            case DECLARE_BLOCKERS:
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
            case END_COMBAT:
                pass(game);
                return false;
            case POSTCOMBAT_MAIN:
//                if (game.getActivePlayerId().equals(playerId)) {
                printOutState(game);
                if (actions.isEmpty()) {
                    calculatePostCombatActions(game);
                }
                act(game);
                return true;
//                }
//                else {
//                    pass(game);
//                }
//                return false;
            case END_TURN:
            case CLEANUP:
                actionCache.clear();
                pass(game);
                return false;
        }
        return false;
    }

    protected void calculatePreCombatActions(Game game) {
        if (!getNextAction(game)) {
            currentScore = GameStateEvaluator2.evaluate(playerId, game);
            Game sim = createSimulation(game);
            SimulationNode2.resetCount();
            root = new SimulationNode2(null, sim, maxDepth, playerId);
            addActionsTimed();
            if (root.children != null && !root.children.isEmpty()) {
                logger.trace("After add actions timed: root.children.size = " + root.children.size());
                root = root.children.get(0);
                // int bestScore = root.getScore();
                // if (bestScore > currentScore || allowBadMoves) {

                // prevent repeating always the same action with no cost
                boolean doThis = true;
                if (root.abilities.size() == 1) {
                    for (Ability ability : root.abilities) {
                        if (ability.getManaCosts().convertedManaCost() == 0 && ability.getCosts().isEmpty()) {
                            if (actionCache.contains(ability.getRule() + '_' + ability.getSourceId())) {
                                doThis = false; // don't do it again
                            }
                        }
                    }
                }
                if (doThis) {
                    actions = new LinkedList<>(root.abilities);
                    combat = root.combat;
                    for (Ability ability : actions) {
                        actionCache.add(ability.getRule() + '_' + ability.getSourceId());
                    }
                }
            } else {
                logger.info('[' + game.getPlayer(playerId).getName() + "][pre] Action: skip");
            }
        } else {
            logger.debug("Next Action exists!");
        }
    }

    protected void calculatePostCombatActions(Game game) {
        if (!getNextAction(game)) {
            currentScore = GameStateEvaluator2.evaluate(playerId, game);
            Game sim = createSimulation(game);
            SimulationNode2.resetCount();
            root = new SimulationNode2(null, sim, maxDepth, playerId);
            logger.debug("Sim Calculate post combat actions ----------------------------------------------------------------------------------------");

            addActionsTimed();
            if (root != null && !root.children.isEmpty()) {
                root = root.children.get(0);
                int bestScore = root.getScore();
                if (bestScore > currentScore || allowBadMoves) {
                    actions = new LinkedList<>(root.abilities);
                    combat = root.combat;
                } else {
                    logger.debug('[' + game.getPlayer(playerId).getName() + "] no better score  current: " + currentScore + " bestScore: " + bestScore);
                }
            } else {
                logger.debug('[' + game.getPlayer(playerId).getName() + "][post] Action: skip");
            }
        }
    }

    @Override
    protected int addActions(SimulationNode2 node, int depth, int alpha, int beta) {
        boolean stepFinished = false;
        int val;
        if (logger.isTraceEnabled() && node != null && node.getAbilities() != null && !node.getAbilities().toString().equals("[Pass]")) {
            logger.trace("Add Action [" + depth + "] " + node.getAbilities().toString() + "  a: " + alpha + " b: " + beta);
        }
        Game game = node.getGame();
        if (ALLOW_INTERRUPT && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return GameStateEvaluator2.evaluate(playerId, game);
        }
        // Condition to stop deeper simulation
        if (depth <= 0 || SimulationNode2.nodeCount > maxNodes || game.checkIfGameIsOver()) {
            val = GameStateEvaluator2.evaluate(playerId, game);
            if (logger.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder("Add Actions -- reached end state  <").append(val).append('>');
                SimulationNode2 logNode = node;
                do {
                    sb.append(new StringBuilder(" <- [" + logNode.getDepth() + ']' + (logNode.getAbilities() != null ? logNode.getAbilities().toString() : "[empty]")));
                    logNode = logNode.getParent();
                } while ((logNode.getParent() != null));
                logger.trace(sb);
            }
        } else if (!node.getChildren().isEmpty()) {
            if (logger.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Add Action [").append(depth)
                        .append("] -- something added children ")
                        .append(node.getAbilities() != null ? node.getAbilities().toString() : "null")
                        .append(" added children: ").append(node.getChildren().size()).append(" (");
                for (SimulationNode2 logNode : node.getChildren()) {
                    sb.append(logNode.getAbilities() != null ? logNode.getAbilities().toString() : "null").append(", ");
                }
                sb.append(')');
                logger.debug(sb);
            }
            val = minimaxAB(node, depth - 1, alpha, beta);
        } else {
            logger.trace("Add Action -- alpha: " + alpha + " beta: " + beta + " depth:" + depth + " step:" + game.getTurn().getStepType() + " for player:" + game.getPlayer(game.getPlayerList().get()).getName());
            if (allPassed(game)) {
                if (!game.getStack().isEmpty()) {
                    resolve(node, depth, game);
                } else {
                    stepFinished = true;
                }
            }

            if (game.checkIfGameIsOver()) {
                val = GameStateEvaluator2.evaluate(playerId, game);
            } else if (stepFinished) {
                logger.debug("Step finished");
                int testScore = GameStateEvaluator2.evaluate(playerId, game);
                if (game.getActivePlayerId().equals(playerId)) {
                    if (testScore < currentScore) {
                        // if score at end of step is worse than original score don't check further
                        //logger.debug("Add Action -- abandoning check, no immediate benefit");
                        val = testScore;
                    } else {
                        /*switch (game.getTurn().getStepType()) {
                            case PRECOMBAT_MAIN:
                                val = simulateCombat(game, node, depth-1, alpha, beta, false);
                                break;
                            case POSTCOMBAT_MAIN:
                                val = simulateCounterAttack(game, node, depth-1, alpha, beta);
                                break;
                            default:
                                val = GameStateEvaluator2.evaluate(playerId, game);
                                break;
                        }*/
                        val = GameStateEvaluator2.evaluate(playerId, game);
                    }
                } else {
                    val = GameStateEvaluator2.evaluate(playerId, game);
                    /*if (game.getTurn().getStepType() == PhaseStep.DECLARE_ATTACKERS)
                        val = simulateBlockers(game, node, playerId, depth-1, alpha, beta, true);
                    else
                        val = GameStateEvaluator2.evaluate(playerId, game);
                     */
                }
            } else if (!node.getChildren().isEmpty()) {
                if (logger.isDebugEnabled()) {
                    StringBuilder sb = new StringBuilder("Add Action [").append(depth)
                            .append("] -- trigger ")
                            .append(node.getAbilities() != null ? node.getAbilities().toString() : "null")
                            .append(" added children: ").append(node.getChildren().size()).append(" (");
                    for (SimulationNode2 logNode : node.getChildren()) {
                        sb.append(logNode.getAbilities() != null ? logNode.getAbilities().toString() : "null").append(", ");
                    }
                    sb.append(')');
                    logger.debug(sb);
                }

                val = minimaxAB(node, depth, alpha, beta);
            } else {
                val = simulatePriority(node, game, depth, alpha, beta);
            }
        }
        node.setScore(val); // test
        logger.trace("returning -- score: " + val + " depth:" + depth + " step:" + game.getTurn().getStepType() + " for player:" + game.getPlayer(node.getPlayerId()).getName());
        return val;

    }

    protected int simulateCombat(Game game, SimulationNode2 node, int depth, int alpha, int beta, boolean counter) {
        Integer val = null;
        if (ALLOW_INTERRUPT && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return GameStateEvaluator2.evaluate(playerId, game);
        }
        if (game.getTurn().getStepType() != PhaseStep.DECLARE_BLOCKERS) {
            game.getTurn().setPhase(new CombatPhase());
            if (game.getPhase().beginPhase(game, game.getActivePlayerId())) {
                simulateStep(game, new BeginCombatStep());
                game.getPhase().setStep(new DeclareAttackersStep());
                if (!game.getStep().skipStep(game, game.getActivePlayerId())) {
                    game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE, null, null, game.getActivePlayerId()));
                    if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, game.getActivePlayerId(), game.getActivePlayerId()))) {
                        val = simulateAttackers(game, node, game.getActivePlayerId(), depth, alpha, beta, counter);
                    }
                } else if (!counter) {
                    simulateToEnd(game);
                    val = simulatePostCombatMain(game, node, depth, alpha, beta);
                }
            }
        } else if (!game.getStep().skipStep(game, game.getActivePlayerId())) {
            game.fireEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_PRE, null, null, game.getActivePlayerId()));
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, game.getActivePlayerId(), game.getActivePlayerId()))) {
                //only suitable for two player games - only simulates blocks for 1st defender
                val = simulateBlockers(game, node, game.getCombat().getDefenders().iterator().next(), depth, alpha, beta, counter);
            }
        } else if (!counter) {
            finishCombat(game);
            ///val = simulateCounterAttack(game, node, depth, alpha, beta);
        }
        if (val == null) {
            val = GameStateEvaluator2.evaluate(playerId, game);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("returning -- combat score: " + val + " depth:" + depth + " for player:" + game.getPlayer(node.getPlayerId()).getName());
        }
        return val;
    }

    protected int simulateAttackers(Game game, SimulationNode2 node, UUID attackerId, int depth, int alpha, int beta, boolean counter) {
        if (ALLOW_INTERRUPT && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return GameStateEvaluator2.evaluate(playerId, game);
        }
        Integer val = null;
        SimulationNode2 bestNode = null;
        SimulatedPlayer2 attacker = (SimulatedPlayer2) game.getPlayer(attackerId);
        UUID defenderId = game.getOpponents(attackerId).iterator().next();
        if (logger.isDebugEnabled()) {
            logger.debug(attacker.getName() + "'s possible attackers: " + attacker.getAvailableAttackers(defenderId, game));
        }
        for (Combat engagement : attacker.addAttackers(game)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Sim Attackers: " + engagement.getAttackers() + ", blockers: " + engagement.getBlockers());
            }
            if (alpha >= beta) {
                logger.debug("Sim Attackers -- pruning attackers");
                break;
            }
            Game sim = game.copy();
            for (CombatGroup group : engagement.getGroups()) {
                for (UUID attackId : group.getAttackers()) {
                    sim.getPlayer(attackerId).declareAttacker(attackId, defenderId, sim, false);
                }
            }
            sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_ATTACKERS, attackerId, attackerId));
            SimulationNode2 newNode = new SimulationNode2(node, sim, depth, attackerId);
            if (logger.isDebugEnabled()) {
                logger.debug("Sim attack for player:" + game.getPlayer(attackerId).getName());
            }
            sim.checkStateAndTriggered();
            while (!sim.getStack().isEmpty()) {
                sim.getStack().resolve(sim);
                logger.debug("Sim attack: resolving triggered abilities");
                sim.applyEffects();
            }
            sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_POST, sim.getActivePlayerId(), sim.getActivePlayerId()));
            Combat simCombat = sim.getCombat().copy();
            sim.getPhase().setStep(new DeclareBlockersStep());
            val = simulateCombat(sim, newNode, depth - 1, alpha, beta, counter);
            if (!attackerId.equals(playerId)) {
                if (val < beta) {
                    beta = val;
                    bestNode = newNode;
                    bestNode.setScore(val);
                    if (!newNode.getChildren().isEmpty()) {
                        bestNode.setCombat(newNode.getChildren().get(0).getCombat());
                    }
                }
            } else if (val > alpha) {
                alpha = val;
                bestNode = newNode;
                bestNode.setScore(val);
                if (!newNode.getChildren().isEmpty()) {
                    bestNode.setCombat(newNode.getChildren().get(0).getCombat());
                }
            }
        }
        if (val == null) {
            val = GameStateEvaluator2.evaluate(playerId, game);
        }
        if (bestNode != null) {
            node.children.clear();
            node.children.add(bestNode);
            node.setScore(bestNode.getScore());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Sim attackers: returning score: " + val + " depth:" + depth + " for player:" + game.getPlayer(node.getPlayerId()).getName());
        }
        return val;
    }

    protected int simulateBlockers(Game game, SimulationNode2 node, UUID defenderId, int depth, int alpha, int beta, boolean counter) {
        if (ALLOW_INTERRUPT && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return GameStateEvaluator2.evaluate(playerId, game);
        }
        Integer val = null;
        SimulationNode2 bestNode = null;
        //check if defender is being attacked
        if (game.getCombat().isAttacked(defenderId, game)) {
            SimulatedPlayer2 defender = (SimulatedPlayer2) game.getPlayer(defenderId);
            if (logger.isDebugEnabled()) {
                logger.debug(defender.getName() + "'s possible blockers: " + defender.getAvailableBlockers(game));
            }
            List<Combat> combats = defender.addBlockers(game);
            for (Combat engagement : combats) {
                if (alpha >= beta) {
                    logger.debug("Sim blockers -- pruning blockers");
                    break;
                }
                Game sim = game.copy();
                for (CombatGroup group : engagement.getGroups()) {
                    if (!group.getAttackers().isEmpty()) {
                        UUID attackerId = group.getAttackers().get(0);
                        for (UUID blockerId : group.getBlockers()) {
                            sim.getPlayer(defenderId).declareBlocker(defenderId, blockerId, attackerId, sim);
                        }
                    }
                }
                sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defenderId, defenderId));
                SimulationNode2 newNode = new SimulationNode2(node, sim, depth, defenderId);
                if (logger.isDebugEnabled()) {
                    logger.debug("Sim block for player:" + game.getPlayer(defenderId).getName());
                }
                sim.checkStateAndTriggered();
                while (!sim.getStack().isEmpty()) {
                    sim.getStack().resolve(sim);
                    logger.debug("Sim blockers: resolving triggered abilities");
                    sim.applyEffects();
                }
                sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_POST, sim.getActivePlayerId(), sim.getActivePlayerId()));
                Combat simCombat = sim.getCombat().copy();
                finishCombat(sim);
                if (sim.checkIfGameIsOver()) {
                    val = GameStateEvaluator2.evaluate(playerId, sim);
                } else if (!counter) {
                    val = simulatePostCombatMain(sim, newNode, depth - 1, alpha, beta);
                } else {
                    val = GameStateEvaluator2.evaluate(playerId, sim);
                }
                if (!defenderId.equals(playerId)) {
                    if (val < beta) {
                        beta = val;
                        bestNode = newNode;
                        bestNode.setScore(val);
                        bestNode.setCombat(simCombat);
                    }
                } else if (val > alpha) {
                    alpha = val;
                    bestNode = newNode;
                    bestNode.setScore(val);
                    bestNode.setCombat(simCombat);
                }
            }
        }
        if (val == null) {
            val = GameStateEvaluator2.evaluate(playerId, game);
        }
        if (bestNode != null) {
            node.children.clear();
            node.children.add(bestNode);
            node.setScore(bestNode.getScore());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Sim blockers: returning score: " + val + " depth:" + depth + " for player:" + game.getPlayer(node.getPlayerId()).getName());
        }
        return val;
    }

    /*protected int simulateCounterAttack(Game game, SimulationNode2 node, int depth, int alpha, int beta) {
        if (ALLOW_INTERRUP && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return GameStateEvaluator2.evaluate(playerId, game);
        }
        Integer val = null;
        if (!game.isGameOver()) {
            simulateToEnd(game);
            game.getState().setActivePlayerId(game.getState().getPlayerList(game.getActivePlayerId()).getNext());
            logger.debug("simulating -- counter attack for player " + game.getPlayer(game.getActivePlayerId()).getName());
            game.getTurn().setPhase(new BeginningPhase());
            if (game.getPhase().beginPhase(game, game.getActivePlayerId())) {
                simulateStep(game, new UntapStep());
                simulateStep(game, new UpkeepStep());
                simulateStep(game, new DrawStep());
                game.getPhase().endPhase(game, game.getActivePlayerId());
            }
            val = simulateCombat(game, node, depth-1, alpha, beta, true);
            if (logger.isDebugEnabled())
                logger.debug("returning -- counter attack score: " + val + " depth:" + depth + " for player:" + game.getPlayer(node.getPlayerId()).getName());
        }
        if (val == null)
            val = GameStateEvaluator2.evaluate(playerId, game);
        return val;
    }*/
    protected void simulateStep(Game game, Step step) {
        if (ALLOW_INTERRUPT && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return;
        }
        if (!game.checkIfGameIsOver()) {
            game.getPhase().setStep(step);
            if (!step.skipStep(game, game.getActivePlayerId())) {
                step.beginStep(game, game.getActivePlayerId());
                game.checkStateAndTriggered();
                while (!game.getStack().isEmpty()) {
                    game.getStack().resolve(game);
                    game.applyEffects();
                }
                step.endStep(game, game.getActivePlayerId());
            }
        }
    }

    protected void finishCombat(Game game) {
        if (ALLOW_INTERRUPT && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return;
        }
        simulateStep(game, new FirstCombatDamageStep());
        simulateStep(game, new CombatDamageStep());
        simulateStep(game, new EndOfCombatStep());
    }

    protected int simulatePostCombatMain(Game game, SimulationNode2 node, int depth, int alpha, int beta) {
        if (ALLOW_INTERRUPT && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return GameStateEvaluator2.evaluate(playerId, game);
        }
        logger.debug("Sim [" + depth + "] -- post combat main");
        game.getTurn().setPhase(new PostCombatMainPhase());
        if (game.getPhase().beginPhase(game, game.getActivePlayerId())) {
            game.getPhase().setStep(new PostCombatMainStep());
            game.getStep().beginStep(game, playerId);
            game.getPlayers().resetPassed();
            return addActions(node, depth, alpha, beta);
        }
        //return simulateCounterAttack(game, node, depth, alpha, beta);
        return GameStateEvaluator2.evaluate(playerId, game);
    }

    protected void simulateToEnd(Game game) {
        if (ALLOW_INTERRUPT && Thread.interrupted()) {
            Thread.currentThread().interrupt();
            logger.debug("interrupted");
            return;
        }
        if (!game.checkIfGameIsOver()) {
            game.getTurn().getPhase().endPhase(game, game.getActivePlayerId());
            game.getTurn().setPhase(new EndPhase());
            if (game.getTurn().getPhase().beginPhase(game, game.getActivePlayerId())) {
                simulateStep(game, new EndStep());
                simulateStep(game, new CleanupStep());
            }
        }
    }

    @Override
    public void setAllowBadMoves(boolean allowBadMoves) {
        this.allowBadMoves = allowBadMoves;
    }
}
