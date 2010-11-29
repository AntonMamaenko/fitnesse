package fitnesse.wikitext.widgets;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VariablesCatalogWidgetTest {
  @Before
  public void setup() {
    TodayWidget.todayForTest = new GregorianCalendar(1952, Calendar.DECEMBER, 5, 1, 13, 23);  //GDTH unix date!!!  Eleven == Dec
  }

  @After
  public void teardown() {
    TodayWidget.todayForTest = null;
  }

  private boolean matches(String widget) {
    return Pattern.matches(TodayWidget.REGEXP, widget);
  }

  private void assertRenders(String widgetString, String result) throws Exception {
    TodayWidget widget = new TodayWidget(new MockWidgetRoot(), widgetString);
    Assert.assertEquals(result, widget.render());
  }

  @Test
  public void shouldMatch() throws Exception {
    assertTrue(matches("!variables"));
    assertTrue(matches("!variables -t"));
    assertTrue(matches("!variables -xml"));
    assertTrue(matches("!variables +3"));
    assertTrue(matches("!variables -3"));
    assertTrue(matches("!variables (MMM)"));
    assertTrue(matches("!variables (MMM) +3"));
  }

  @Test
  public void shouldNotMatch() throws Exception {
    assertFalse(matches("!variables -p"));
    assertFalse(matches("!variables 33"));
    assertFalse(matches("!variables x"));
  }

  @Test
  public void today() throws Exception {
    assertRenders("!variables", "05 Dec, 1952");
  }

  @Test
  public void withTime() throws Exception {
    assertRenders("!variables -t", "05 Dec, 1952 01:13");
  }

  @Test
  public void xml() throws Exception {
    assertRenders("!variables -xml", "1952-12-05T01:13:23");
  }

  @Test
  public void addOneDay() throws Exception {
    assertRenders("!variables +1", "06 Dec, 1952");
  }

  @Test
  public void subtractOneDay() throws Exception {
    assertRenders("!variables -1", "04 Dec, 1952");
  }

  @Test
  public void subtractOneWeek() throws Exception {
    assertRenders("!variables -7", "28 Nov, 1952");
  }

  @Test
  public void addOneYear() throws Exception {
    assertRenders("!variables +365", "05 Dec, 1953");
  }

  @Test
  public void format() throws Exception {
    assertRenders("!variables (MMM)", "Dec");
  }

  @Test
  public void formatPlusOneDay() throws Exception {
    assertRenders("!variables (ddMMM) +1", "06Dec");
  }

}
