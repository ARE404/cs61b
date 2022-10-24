package byog.Core;

import byog.TileEngine.TETile;

import java.awt.Point;
import java.util.ArrayList;

public class HallWay {

    public static ArrayList<Room> computeHallWay(ArrayList<ArrayList<Block>> blockList) {
        ArrayList<Room> res = new ArrayList<>();
        res.addAll(computeRowHallWay(blockList));
        res.addAll(computeColHallWay(blockList));
        return res;
    }

    private static ArrayList<Room> computeRowHallWay(ArrayList<ArrayList<Block>> blockList) {
        ArrayList<Room> res = new ArrayList<>();
        for (int y = 0; y < WorldGenerator.getBlockNumY(); ++y) {
            ArrayList<Block> rowBlockList = new ArrayList<>();
            for (int x = 0; x < WorldGenerator.getBlockNumX(); ++x) {
                rowBlockList.add(blockList.get(x).get(y));
            }
            ArrayList<Block> blocksWithRoom = blocksWithRoom(rowBlockList);
            int size = blocksWithRoom.size();
            if (size == 2) {
                res.add(hallWayBetweenHBlocks(blocksWithRoom.get(0), blocksWithRoom.get(1)));
            } else if (size > 2) {
                for (int i = 0; i < size - 1; ++i) {
                    if (RandomUtils.bernoulli(WorldGenerator.getRANDOM(), 0.8)) {
                        Block blockI = blocksWithRoom.get(i);
                        res.add(hallWayBetweenHBlocks(blockI, blocksWithRoom.get(i + 1)));
                    }
                }
            }
        }
        return res;
    }

    private static ArrayList<Room> computeColHallWay(ArrayList<ArrayList<Block>> blockList) {
        ArrayList<Room> res = new ArrayList<>();
        for (ArrayList<Block> colBlockList : blockList) {
            ArrayList<Block> blocksWithRoom = blocksWithRoom(colBlockList);
            int size = blocksWithRoom.size();
            if (size == 2) {
                res.add(hallWayBetweenVBlocks(blocksWithRoom.get(0), blocksWithRoom.get(1)));
            } else if (size > 2) {
                for (int i = 0; i < size - 1; ++i) {
                    if (RandomUtils.bernoulli(WorldGenerator.getRANDOM(), 0.8)) {
                        Block blockI = blocksWithRoom.get(i);
                        res.add(hallWayBetweenVBlocks(blockI, blocksWithRoom.get(i + 1)));
                    }
                }
            }
        }
        return res;
    }

    /**
     * Return a block arraylist from another block list which all has a room.
     */
    private static ArrayList<Block> blocksWithRoom(ArrayList<Block> blocks) {
        ArrayList<Block> res = new ArrayList<>();
        for (Block b : blocks) {
            if (b.hasRoom()) {
                res.add(b);
            }
        }
        return res;
    }

    /**
     * Return the hallway between two blocks.
     */
    public static Room hallWayBetweenVBlocks(Block block1, Block block2) {
        if ((!(block1.hasRoom() && block2.hasRoom())) || (block1.getPos().x != block2.getPos().x)) {
            return null;
        }

        // judge relative position
        Room roomAbove;
        Room roomBelow;
        if (block1.getPos().y > block2.getPos().y) {
            roomAbove = block1.getRoom();
            roomBelow = block2.getRoom();
        } else {
            roomAbove = block2.getRoom();
            roomBelow = block1.getRoom();
        }

        // compute overlap length
        int minRight = Math.min(roomAbove.getRoomTopRightPos().x,
                roomBelow.getRoomTopRightPos().x);
        int maxLeft = Math.max(roomAbove.getRoomPos().x,
                roomBelow.getRoomPos().x);
        int overlapLength = Math.max(0, minRight - maxLeft);

        // if not overlap return
        if (overlapLength == 0) {
            return null;
        }

        // compute pos
        int hallWayX = maxLeft + (overlapLength / 2);
        int hallWayY = roomBelow.getRoomPos().y + (roomBelow.getWidth() / 2);
        Point hallWayPos = new Point(hallWayX, hallWayY);

        // compute length
        int endY = roomAbove.getRoomPos().y + (roomAbove.getWidth() / 2);
        int startY = roomBelow.getRoomPos().y + (roomBelow.getWidth() / 2);
        int hallWayLength = endY - startY;

        block1.setConnected(true);
        block2.setConnected(true);
        return new Room(hallWayPos, 2, hallWayLength);
    }

    public static Room hallWayBetweenHBlocks(Block block1, Block block2) {
        if ((!(block1.hasRoom() && block2.hasRoom())) || (block1.getPos().y != block2.getPos().y)) {
            return null;
        }

        // judge relative position
        Room roomLeft;
        Room roomRight;
        if (block1.getPos().x < block2.getPos().x) {
            roomLeft = block1.getRoom();
            roomRight = block2.getRoom();
        } else {
            roomLeft = block2.getRoom();
            roomRight = block1.getRoom();
        }

        // compute overlap length
        int minTop = Math.min(roomLeft.getRoomTopRightPos().y
                            , roomRight.getRoomTopRightPos().y);
        int maxBottom = Math.max(roomLeft.getRoomPos().y
                                , roomRight.getRoomPos().y);
        int overlapLength = Math.max(0, minTop - maxBottom);

        // if not overlap return
        if (overlapLength == 0) {
            return null;
        }

        int hallWayPosX = roomLeft.getRoomPos().x + (roomLeft.getWidth() / 2);
        int hallWayPosY = maxBottom + (overlapLength / 2);
        Point hallWayPos = new Point(hallWayPosX, hallWayPosY);

        int startX = roomLeft.getRoomPos().x + (roomLeft.getWidth() / 2);
        int endX = roomRight.getRoomPos().x + (roomRight.getWidth() / 2);
        int hallWayLength = endX - startX;

        block1.setConnected(true);
        block2.setConnected(true);
        return new Room(hallWayPos, hallWayLength, 2);
    }

    public static void drawHallWay(TETile[][] world, ArrayList<Room> hallWays) {
        if (hallWays.size() == 0) {
            return;
        }
        for (Room hallWay : hallWays) {
            Room.drawRoom(world, hallWay);
        }
    }
}
