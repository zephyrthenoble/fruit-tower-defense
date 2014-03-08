    public class Grid
   {
      HashMap<Location, GameObject> grid;
      int width;
      int height;
       public Grid(int x,int y)
      {
         width=w;
         height=y;
         HashMap<Location, GameObject> grid=new Hashmap<Location, GameObject>();
      }
       public boolean isValid(Location loc)
      {
         return loc.xPos>=0
            &&
            loc.xPos<width
            &&
            loc.yPos>=0
            &&
            loc.yPos<height;
      }
       public Location get(Location loc)
      {
         return grid.get(loc);
      }
   }