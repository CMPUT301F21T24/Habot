import static org.junit.Assert.assertEquals;

import com.example.habot.Habit;
import com.example.habot.Habitlist;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class HabitListTest {
    private Habitlist habitlist;

    @Before
    public void CreateHabitList(){
        habitlist = new Habitlist(null, new ArrayList<Habit>());
        habitlist.add(new Habit("Basketball","exercising","9-13"));

    }

    @Test
    public void AddHabitTest(){
        int listSize = habitlist.getCount();
        habitlist.AddHabit(new Habit("Google", "Study","8-8"));
        assertEquals(habitlist.getCount(), listSize+1);
    }

    @Test
    public void DeleteHabitTest(){
        int listSize = habitlist.getCount();
        if (listSize > 0){
            habitlist.DeleteHabit((listSize-1));
            assertEquals(habitlist.getCount(), listSize-1);
        }
    }
}
