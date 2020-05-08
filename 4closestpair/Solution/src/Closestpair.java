import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Closestpair {

    public Pair<Point> closestPair(List<Point> pointList)
    {
        Pair<List<Point>> sortedPoints = new DoubleMergeSort<Point>().doubleSort(pointList, new Comparator<Point>() {
        @Override
        public int compare(Point point, Point t1) { return point.x - t1.x; }
    }, new Comparator<Point>() {
        @Override
        public int compare(Point point, Point t1) { return point.y-t1.y; }
    });
        return closest(sortedPoints.first, sortedPoints.second);
    }

    private Pair<Point> bruteForce (List<Point> px)
    {
        Pair<Point> min = new Pair<>(px.get(0),px.get(1));
        for(int i = 0; i<px.size(); ++i)
            for (int j = i+1; j<px.size(); ++j)
                if(px.get(i).distanceTo(px.get(j))<min.first.distanceTo(min.second)){ min = new Pair<>(px.get(i),px.get(j));}
        return min;
    }

    private Pair<Point> closest (List<Point> px, List<Point> py)
    {
        //Om vi har 3 eller mindre punkter kan vi bruteforca
        if(px.size() <= 3)
        {
            return bruteForce(px);
        }

        //Vi delar px och py upp i sublister
        List<Point> lx = px.subList(0, px.size()/2);
        List<Point> rx = px.subList(px.size()/2, px.size());

        List<Point> ly = /*py.subList(0,py.size()/2);*/ new ArrayList<>();
        List<Point> ry = /*py.subList(py.size()/2, py.size());*/ new ArrayList<>();

        for(int i =0; i< py.size(); ++i)
        {
            if(py.get(i).x<rx.get(0).x) ly.add(py.get(i));
            else ry.add(py.get(i));
        }



        Pair<Point> left = closest(lx, ly);
        Pair<Point> right = closest(rx, ry);

        Pair<Point> delta = left;
        if(left.first.distanceTo(left.second)>right.first.distanceTo(right.second))
            delta = right;


        //denna kommer köra max 6 gånger
        for(int i = 0; i<py.size() && i<15; ++i)
            for (int j = i+1; j<py.size() && (py.get(j).y - py.get(i).y)<delta.first.distanceTo(delta.second); ++j)
                if(py.get(i).distanceTo(py.get(j))<delta.first.distanceTo(delta.second))
                {
                   // System.out.println(i + " " + j + py);
                    delta = new Pair<>(py.get(i), py.get(j));}
        return delta;

    }
}
