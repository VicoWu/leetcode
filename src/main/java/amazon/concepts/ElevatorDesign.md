系统设计：电梯问题

[这里](http://www.cnblogs.com/leetcode/p/4096365.html)有对这个题目的解释

我的设计方案：
Building对象代表一栋楼，这栋楼含有多个楼层，用Floor表示。

一个电梯用一个Elevator对象表示。一个Elevator对象的基本属相应该就是可以停靠的楼层，比如，一栋高楼，有的电梯是低层电梯，有的电梯
是高层专用电梯。

电梯停靠由两种原因，一种是电梯内部有人按下了某个目标楼层，而是有人在楼层外按下向上或者向下的请求，并且当前电梯的运行方向与请求方向一致。
第一种请求是在电梯里面，因此由Elevator自己管理
第二种请求是楼层的每一层有一个向上或者向下或者同时都有向上向下的请求，因此交给楼层Floor对象进行管理。
```
public class Elevator{
    
    private final Integer maxWeight;
    private final Set<Floor> availableFloor;
    Map<Integer,TargetFloorRequest> targetFloorRequest;
    
    
    private Direction currentDirection; //当前的运行方向
    private Floor highestFloor;
    private Integer currentFloor;
    private boolean available;
    
    public Elevator(Building building,Integer maxWeight,Set<Floor> availableFloor, Floor highestFloor){
      this.building = building;
      this.maxWeight = maxWeight;
      this.availableFloor = availableFloor;
      this.highestFloor = highestFloor;
    }
   
    public void startup(){
       this.available = true;
       currentFloor = 1; //初始状态下，电梯在一楼
       while(true){ //每到达一个楼层，都需要检测当前楼层是否有请求，包括电梯外的上下运行请求或者电梯内部的请求

         if(targetFloorRequest.containsKey(currentFloor.floorNum)  //如果电梯里面有用户请求在此楼层下电梯
         || availableFloor.contains(new Floor(currentFloor)) && building.getPickInRequest()!=null) //或者该楼层外面有请求上或者下电梯并且方向与电梯当前运行方向一致
          { //两种情况任意一种发生，则停靠
          building.getFloor(currentFloor).cancelRequest(currentDirection); //电梯到达该楼层，方向匹配，接入乘客，那么该楼层这个方向的请求应该取消
          openDoor();
          waitUntilAllowedWeight(); //等待直到电梯不超载
          closeDoor();
          while( runToNextFloor(currentDirection).equals( currentFloor.floorNum)) //获取下一层
          {
            //如果发现算法返回的nextFloor就是当前楼层，说明当前没有了任何请求，因此电梯停止并等待，直到有了新的请求
            sleep(1000);
          }
        
       }
    }
     
    //如果当前运行方向是向上，那么查看有没有这个楼层以上的请求，如果有，那么下一个楼层就是当前楼层加1或者减1，注意，比如当前在3楼，然后当前8楼有一个请求，这个方法会返回4楼，必须一层一层往上，因为运行过程中也有可能有新的请求过来
    private Integer runToNextFloor(Floor currentFloor , Direction currentDirection){ //运行到下一楼层
     
        if(currentFloor == 1)
             currentDirection  = UP;
        if(currentFloor == highestFloor.floorNum)
             currentDirection  = DOWN;
                       
        if(currentDirection.eqals(UP))
            {
             while()
            
            }
        if()
    }
    
    private void openDoor(){ //关门
    
    }
    
    private void closeDoor(){  //开门
    
    }
    
    private void repair(){
        this.available =  false;
    }
    
    //阻塞，直到不超载
    private void waitUntilAllowedWeight(){
        while(overWeight()){
          alarm("Over weight.");
          sleep(1000);
        }
    }
    
    
    private boolean overWeight(){ //是否超载
       return getCurrentWeight() > maxWeight;
    }
    
    private Integer getCurrentWeight(){
          //电梯获取当前载重
    }
}

public class Building{
   
     Map<Integer,Floor> floors;
     public Builder(Map<Integer,Floor> floors){
        this.floors = floors;
     }
     
     public PickInRequest getPickInRequest(Integer floorNumber,Direction direction){
        if(floors.containsKey(floorNumber) )
           return floors.get(floorNumber).getRequest(direction);
        return null;
     }
     
     
     public Floor getFloor(Integer floorNum){
         return floors.get(floorNum);
     }
}



public class Floor{
     private final Integer floorNum; //楼层编号，不可修改
     private Elevator[] allowedElevator; //哪些电梯允许在本楼层停靠，可修改
     private Set<PickInRequest> request;
     
     
     public Floor(Integer floorNum,Elevator[] allowedElevator){
        this.floorNum = floorNum;
        this.allowedElevator = allowedElevator;
     }

     //只要楼层编号相同，就认为是同一楼层
     public boolean equals(Object o){
         if(o instanceof Floor  && ((Floor)o).floorNum == this.floorNum)
           return true;
         return false;
     }
     
     /*
     用户在某个楼层按下请求向上或者向下的按钮
     */
     
     public void addRequest(Direction direction){
         this.request.add( new PickInRequest(direction));
     }
     
     /*
     电梯在该楼层接上了人，取消pickingRequest
     */
     public void cancelRequest(Direction direction){
         this.request.remove(new PickInRequest(direction));
     }
     
     public PickInRequest getRequest(Direction direction){ //返回这个楼层对应的请求
         return request.contains(new PickInRequest(direction))?new PickInRequest(direction):null;
     }
     
}

public class TargetFloorRequest{
   
}

public class PickInRequest{
   
   public Direction direction; //请求方向
    // 同一楼层，只要方向相同个，就认为是同一请求
   public boolean equals(Object o){
     return (o instanceof PickInRequest) && ((Direction)o.direction).equals(this.direction);
   }

}


   
   public static  enum  Direction{
         UP,DOWN;
   }
   
   
```