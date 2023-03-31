![logo](img/logo.png)

---

## What is this?
> This framework allows you to configure, schedule, migrate and run tasks
> <br>and processes of various kinds with simple manipulations!

---

## How to use?
> The API architecture works on the principle of a regular calendar. 
> <br><br>Let's walk through the trivial steps that take place at the level of
> user interaction with the calendar:

```java
import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.factory.PlanoCalendars;

public class TestStart {

  // method for running anyone task.
  public void startTask() {

    // create a calendar.
    PlanoCalendar planoCalendar = PlanoCalendars.createSingleThreadCalendar();

    // get a scheduling service
    PlanoScheduler scheduler = planoCalendar.getScheduler();
  }
}
```

> Okay, the next step is for the user to create some kind of plan of action 
> <br>for a certain time, mark with cuts, etc:

```java
TaskPlan plan = planoScheduler.configurePlan(TaskParamCacheBuilder.create()
    .set(TaskParams.TASK_TIME_UNIT, TimeUnit.SECONDS)
    .set(TaskParams.TASK_DELAY, 3L)
    .build());
```

---

> At this point we can draw a line, because the basic requirements for the configuration of the planned task we carried out

> The framework currently has a lot of different parameters, below I will provide a complete list of them:

```java
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.param.TaskParamKey;
import java.util.concurrent.TimeUnit;

public interface TaskParams {

  /**
   * Параметр отвечает за остановку активного
   * процесса запланированной задачи во время ее отмены.
   */
  TaskParamKey<Boolean> INTERRUPT_ON_CANCEL = new TaskParamKey<>("INTERRUPT_ON_CANCEL", Boolean.TRUE);

  /**
   * Параметр отвечает за единицу измерения
   * времени, по которому будет идти отсчет
   * запланированной задачи.
   */
  TaskParamKey<TimeUnit> TASK_TIME_UNIT = new TaskParamKey<>("TASK_TIME_UNIT");

  /**
   * Время, в течении которого запланированная
   * задача выполнит свой первый шаг.
   */
  TaskParamKey<Long> TASK_DELAY = new TaskParamKey<>("TASK_DELAY");

  /**
   * Во-первых, данный параметр добавляет запланированной
   * задаче цикличное выполнение одного и того
   * же процесса.
   * <p>
   * Во-вторых, данный параметр отвечает за переодичность
   * циклично выполняемых процессов запланированной
   * задачи.
   */
  TaskParamKey<Long> TASK_REPEAT_DELAY = new TaskParamKey<>("TASK_REPEAT_DELAY", 1L);

  /**
   * Параметр дает разрешение на цикличное выполнение
   * обработки ответа из процесса запланированной задачи
   * (Работает вместе с TASK_REPEAT_DELAY)
   */
  TaskParamKey<Boolean> TASK_REPEAT_RESPONSE_HANDLING = new TaskParamKey<>("TASK_REPEAT_RESPONSE_HANDLING",
      Boolean.FALSE);

  /**
   * Устанавливает максимальное время ожидания ответа
   * из процесса запланированной задачи при вызове
   * метода {@link PlanoTask#awaitTermination()}
   */
  TaskParamKey<Long> AWAIT_TERMINATION_TIMEOUT = new TaskParamKey<>("AWAIT_TERMINATION_TIMEOUT", 15_000L);
}
```

---

> In the next step, we already put the planned task into action.

> For this we have two possible options:
> 1.  A task followed by a response from it:

```java
ResponsedTaskProcess<String> process = (response) -> response.complete("Hello world!");
PlanoTask<String> task = scheduler.scheduleResponsed(plan, process);

try {
    String response = task.awaitResponse();
    System.out.println(response);
    
} catch (PlanoNonResponseException exception) {
    exception.printStackTrace();  
}
```

> 2. A simple task that performs the task assigned to it:

```java
TaskProcess process = () -> System.out.println("Hello world!");
PlanoTask<?> task = scheduler.schedule(plan, process);
```

---

## Testing

> And this is not all of the possible functionalities that
> <br>this framework implies.

> More information on usage can be found by following 
> <br>the <a href="https://github.com/ItzStonlex/plano-framework/tree/main/src/test/java/com/github/itzstonlex/planoframework/test">direct link</a> to the internal **unit-tests**

---

## Support development

<div align="center">

> **Development by <a href="https://github.com/ItzStonlex">@ItzStonlex</a>**
> <br>We can support me here:
> <br><br><a href="https://www.buymeacoffee.com/itzstonlex" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a>

</div>

---