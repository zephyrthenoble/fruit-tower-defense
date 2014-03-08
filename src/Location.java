   import java.util.ArrayList;
    public class Location
   {
      int xPos=0;
      int yPos=0;
		
      boolean walkable;
       public Location(int xP,int yP)
      {
         xPos=xP;
         yPos=yP;
      
      }
		public void setX(int x)
		{
		xPos=x;
		}
		public void setY(int y)
		{
		yPos=y;
		}
       public ArrayList<Location> getNeighbors()
      {
         ArrayList<Location> locations=new ArrayList<Location>();
         for(int x=-2;x<=2;x++)
            for(int y=-2;y<=2;y++)
            {
               Location temp=grid.get(x+xPos,y+yPos);
               if(grid.isValid(temp)&&!this.equals(temp))
               {
                  locations.add(temp);
               }
            }
         return locations;
      }
       public boolean equals(Location loc)
      {
         return loc.xPos==this.xPos&&loc.yPos==this.yPos;
      }
   }