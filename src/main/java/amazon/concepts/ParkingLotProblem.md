 
 系统设计：停车场问题
 
 
 # 别人的设计方案

```
/*
所有车辆的基类，留下park和unpark给各种不同的车辆的实现类自己实现
*/
public abstract class Vehicle {
   private final String id; //每辆车的id是不可修改的，只可以在构造的时候设置进来
   public Vehicle(String id){
      this.id = id;
   }
   protected ParkingSpace pSpace;
   public abstract boolean park(ParkingLot pLot);
   public boolean unpark(ParkingLot pLot)P{
       if(pSpace!=null){
           pLot.reclaimFreeSpace(pSpace);
           return true;
       }
       return false;
   }
}

//将车辆分成不同的类别，
public class Cars extends Vehicle{

    @Override
    public boolean park(ParkingLot pLot) { //其实这里的设计不好，因为能否停放不是由车辆自己决定的，应该是停车场决定的
        if( (pSpace = pLot.allocateFreeSpace(ParkingSpace.ParkingSpaceType.CAR)) ==null)
            return true;
        else
            return false;
    }

    
}



public class Motorbike extends Vehicle{
    @Override
    public boolean park(ParkingLot pLot) {
        if( (pSpace = pLot.allocateFreeSpace(ParkingSpace.ParkingSpaceType.MOTOBIKE)) ==null)
            return true;
        else
            return false;
    }
    
}



public class HandicappedCars extends Vehicle{

    @Override
    public boolean park(ParkingLot pLot) {
        if( (pSpace = pLot.allocateFreeSpace(ParkingSpace.ParkingSpaceType.HANDICAPPED)) ==null)
            return true;
        else
            return false;
    }

}



import java.sql.Time;
import java.util.GregorianCalendar;

public class ParkingSpace {

    public enum ParkingSpaceType
    {
        MOTOBIKE, CAR, HANDICAPPED;
    }
    
    private int id;
    private ParkingSpaceType pSpaceType;
    private ParkingMeter meter;
    
     public ParkingSpace(int id, ParkingSpaceType pspaceType)
     {
            super();
            this.id = id;
            this.pSpaceType = pspaceType;
     }
     
     public int getId()
        {
            return id;
        }
       public void setStart()
        {
            meter.start = new GregorianCalendar();
        }
        public void setEnd()
        {
            meter.end = new GregorianCalendar();
        }
        
        
     public ParkingSpaceType getpSpaceType(){
         return pSpaceType;
     }
     
     private class ParkingMeter{
         public GregorianCalendar start;
         public GregorianCalendar end;
     }
     
      public float getFee()
      {
            return perm.getFee(meter.start, meter.end);
      }

}




import java.util.List;

import parking.ParkingSpace.ParkingSpaceType;

public class ParkingLot {

       private List<ParkingSpace> freeRegularSpace; //停放摩托车
       private List<ParkingSpace> freeCompactSpace; //停放小汽车
       private List<ParkingSpace> freeHandicappedSpace; //停放残疾人车辆
        
       public ParkingLot(){};
       
       public ParkingSpace allocateFreeSpace(ParkingSpaceType pSpaceType) throws Exception{
           ParkingSpace pSpace = null;
           
           switch(pSpaceType)
           {
           case MOTOBIKE:
               if(freeRegularSpace.size()==0)
                   return null;
               pSpace = freeRegularSpace.remove(0);
               pSpace.setStart();
               break;
               
           case HANDICAPPED:
               if(freeHandicappedSpace.size()==0)
                   return null;
               pSpace = freeHandicappedSpace.remove(0);
               pSpace.setStart();
               break;
               
           case CAR:
               if(freeCompactSpace.size()==0)
                   return null;
               pSpace = freeCompactSpace.remove(0);
               pSpace.setStart();
               break;
               
           default:
               throw new Exception("Invalid String");
           }
           
           public float reclainFreeSpace(ParkingSpace Space){
               Space.setEnd();
               switch(Space.getpSpaceType()){
               case MOTOBIKE:
                   freeRegularSpace.add(Space);
                   break;
                   
               case CAR:
                   freeCompactSpace.add(Space);
                   break;
                   
               case HANDICAPPED:
                   freeHandicappedSpace.add(Space);
                   break;
                
               default:
                   break;
               }
               
               return Space.getFee();
               
               
           }
           
           
       }

}
```

# 我的设计方案
下面的方案是在[这里](http://www.cnblogs.com/leetcode/p/3889233.html)的方案
我的设计方案：
一个Vehicle抽象类，所有车辆的共享信息，如车牌号，车牌号不可修改
```
public Class Vehicle{
   protected String id;
   protected VehicleType type;
   
   public static enum VehicleType{ //车辆类型，直接放在Vehicle中，是Vehicle的内部类，无需放在别的地方
   CAR,MOTORBIKE,HANDICAPPEDCARS;
   }
   
   public Vehicle(String id,VehicleType type ){
       this.id = id;
       this.type = type;
   }
}

```
各种车辆的实现类Car、Motorbike、HandicappedCars，比如小汽车、残疾人车辆、摩托车等，每个车辆都有一个构造函数，用来设置车牌号和车辆类型的enum变量
```$xslt
public Class Car{
     public Car(String id,String type){
        super(id,type);
     }
}
```



停车场对象ParkingLot，用来代表整个停车场。一个停车场对象应该包含了三个管理者，CarSpaceManager，MotorbikeSpaceManager和HandicappedCarSpaceManager,每个管理者负责管理各自类型
```$xslt

public class ParkingLot{
private CarSpaceManager csm; 
private MotorbikeSpaceManager msm;
private HandicappedCarSpaceManager hcsm;

/*
初始化整个停车场，就是初始化各个空间管理器，比如小汽车空间管理器，摩托车空间管理器等等
*/
public ParkingLot(){
  this.csm = new CarSpaceManager(100,10);//可容纳100辆车，每分钟收费10元
  this.msm = new CarSpaceManager(2000，2);
  this.hcsm  = new HandicappedCarSpaceManager(20,1);
}


public boolean park(String id,Vehicle.VehicleType type){ //一个车牌号为id、类型为type的车辆要停车
   //根据type找到对应的空间管理器
  SpaceManager  manager =  findSpaceManagerByType();
  return manager.park(); //停车场的park方法应该就是调用具体的空间管理器的方法

}

public void unpark(String id,Vehicle.VehicleType type){ //一个车牌号为id的车辆驶出停车场

    SpaceManager  manager =  findSpaceManagerByType();
    Double price = manager.unpark();
    //收费
    //放行
}

/*
空间管理器的基类
*/
public SpaceManager{
   private Integer totalSpace; //容量
   private Map<String,ParkingVehicleInfo> parkingVehicles; //当前已经停放的车辆
   private Double priceEachMinute; //单价
   public SpaceManager(Integer totalSpace , Double priceEachMinute){
      this.totalSpace = totalSpace;
      this.priceEachMinute = priceEachMinute;
      parkingVehicles = new HashMap<String,ParkingVehicleInfo>();
   }
   
   public boolean park(Vehicle vehicle){
     if(parkingVehicles.size() < totalSpace)
        {
         parkingVehicles.put(vehicle.id,vehicle)
         return true;
        }
      return false;
   }
   
   public Double unpark(String id){
         ParkingVehicleInfo pvi = parkingVehicles.remove(id);
         return this.getCost(pvi);
   }
   
   public static  class ParkingVehicleInfo{ //ParkingVehicleInfo封装了停车信息，包括了车辆信息和车辆的停车开始时间
       private Vehicle vehicle;
       private Date start;
   }
   
   public Price getCost(ParkingVehicleInfo pvi){ //获取应收取的费用
   return 1;
   }
   
}





```
的停车空间下面的车辆停放情况，他们都继承一个SpaceManager