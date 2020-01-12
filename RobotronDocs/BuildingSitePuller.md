# Overview #
We want our building site puller to be able to pull the building site base to the building site in the Autonomous period and back out again in the endgame.

# Design Considerations #

#### Motor considerations ####
We decided to use servo motors because we thought that the building site puller wouldn't need a lot of power compared to other actions of our robot.  Because of this, we used two servos to pull back the building site. That way we could keep our motors for other tasks such as the block picker.

#### Motor safety ####
Since hooking the servo motor straight to the piece that rotates to pull the base could damage the servo, we added an extra gear to minimize the extra strain on it.

#### Speed considerations ####
We wanted our puller to go fast so we can complete pulling out the base during autonomous and don't waste time in the endgame so we used a 40 tooth gear connecting to an eighty tooth gear  instead of having a 20 tooth gear connected  to an 80 tooth gear.