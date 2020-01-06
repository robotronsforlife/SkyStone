#Using Camera#





## Camera placement##
For autonomous we are using our camera to detect blocks. We decided to place our camera in the front center of the robot, at the bottom. We placed it in the center because then we wouldn't have to do extra programming so that the center of the robot was facing the center of the block. We put it at the bottom front because then the camera would be in a straight line from the block, and the proportions of the block wouldn't be messed up in the camera. Ex: selfies. When we take selfies we hold it at an angle above us, and even though our faces seem normal sized our bodies seem way smaller than normal. Since we don't want our blocks proportions to be like this we kept the camera directly in front of the block instead of at the top. Also keeping the camera near the bottom of the robot helped effectively conserve space.
## Vuforia Detection##
 We used Vuforia to program our camera. Vuforia is an augmented reality software development kit (SDK) for mobile devices that uses computer vision technology to recognize and track Image Targets in real time. [Know more about Vuforia.](https://firstroboticsbc.org/wp-content/uploads/2018/10/Introduction-to-Vuforia.pdf) We used vuforia in our program to detect the skystone block once our robot got close to it. The sample program we added onto also gave the x, y, and z position of the block. We used this to make sure the robot was the optimal position in front of the block. In our case the optimal position is about 15 inches behind the block, and directly in the center of the block, so that we can grab it with our arm. See Arm section.




## New Plan as of some day in December

INSERT A DATE OR SOMETHING HERE TO SHOW THAT THIS HAPPENED LATER Probably into heading

Now we have decided that instead of using our main stacking arm to grab the blocks during autonomous we added another attachment similar to our base puller attachment to grab blocks. We are going to use the arm to grab as many blocks(probably four) as we can to the other side of the bridge before autonomous ends. However instead of randomly grabbing blocks we are going to first detect the skystone, bring it across and then we will go back for the other blocks. We are planning to only detect the first of the two skystones and then will just grab three other blocks by it.  
