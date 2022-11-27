package com.example.handyMan.application.services;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.application.ports.in.WeekCalculatorUseCase;
import com.example.handyMan.application.ports.out.ReportRepository;
import com.example.handyMan.application.ports.out.WeekCalculatorRepository;
import com.example.handyMan.infrastructure.models.DTO.WeekCalculatorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class WeekCalculatorService implements WeekCalculatorUseCase
{
    @Autowired
    private WeekCalculatorRepository weekCalculatorRepository;

    @Override
    public WeekCalculatorDTO weekCalculator(String idTechnical, int week)
    {
        List<Report> reports = weekCalculatorRepository.findWeek(idTechnical, week);
        reports.sort(Comparator.comparing(Report::getStartDate));
        return calculatorHours(reports);
    }

    private WeekCalculatorDTO calculatorHours(List<Report> reports)
    {
        Calendar calendar = Calendar.getInstance();
        WeekCalculatorDTO weekCalculatorDTO = new WeekCalculatorDTO();
        int dayOfWeek, dayOfWeek2;
        int hour, hour2;
        double totalHours = 0;
        double total = 0;
        for (Report report : reports)
        {
            calendar.setTime(report.getStartDate());
            dayOfWeek = calendar.get(Calendar.DAY_OF_YEAR);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            calendar.setTime(report.getEndDate());
            dayOfWeek2 = calendar.get(Calendar.DAY_OF_YEAR);
            hour2 = calendar.get(Calendar.HOUR_OF_DAY);
            //Validamos si es el mismo dia
            if (dayOfWeek == dayOfWeek2)
            {
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                //Validamos si es domingo
                if (dayOfWeek != 1)
                {
                    //Validamos si se encuentra en el rango de 7am a 8pm
                    if (hour >= 7 && hour2 <= 20)
                    {
                        total = convertMilitoHour(report.getEndDate(), report.getStartDate());
                        calculatorNM(weekCalculatorDTO, total, totalHours);
                    }//Validamos si se encuentra en el rango de 8pm a 7pm
                    else if ((hour >= 20 && hour2 < 24) || (hour >= 0 && hour2 <= 7))
                    {
                        total = convertMilitoHour(report.getEndDate(), report.getStartDate());
                        calculatorNC(weekCalculatorDTO, total, totalHours);
                    } else
                    {
                        boolean inter = false;
                        //Validamos el inicio de la hora
                        if (hour >= 7 && hour < 20)
                        {
                            total = convertMilitoHour(conditionalsDate(report.getStartDate(), 20), report.getStartDate());
                            calculatorNM(weekCalculatorDTO, total, totalHours);
                        } else
                        {
                            if (hour >= 20 && hour < 24)
                            {
                                total = convertMilitoHour(conditionalsDate(report.getStartDate(), 24), report.getStartDate());
                            } else if (hour >= 0 && hour < 7)
                            {
                                inter = true;
                                total = convertMilitoHour(conditionalsDate(report.getStartDate(), 7), report.getStartDate());
                            }
                            calculatorNC(weekCalculatorDTO, total, totalHours);
                        }
                        totalHours += total;
                        //Validamos el final de la hora
                        if (hour2 >= 7 && hour2 < 20)
                        {
                            total = convertMilitoHour(report.getEndDate(), conditionalsDate(report.getEndDate(), 7));
                            calculatorNM(weekCalculatorDTO, total, totalHours);
                        } else
                        {
                            if (hour2 >= 20 && hour2 < 24)
                            {
                                if (inter)
                                {
                                    total = 13;
                                    calculatorNM(weekCalculatorDTO, total, totalHours);
                                    totalHours += total;
                                }
                                calendar.setTime(report.getStartDate());
                                total = convertMilitoHour(report.getEndDate(), conditionalsDate(report.getEndDate(), 20));
                            } else if (hour2 >= 0 && hour2 < 7)
                            {
                                total = convertMilitoHour(report.getEndDate(), conditionalsDate(report.getEndDate(), 0));
                            }
                            calculatorNC(weekCalculatorDTO, total, totalHours);
                        }
                    }
                } else
                {
                    total = convertMilitoHour(report.getEndDate(), report.getStartDate());
                    calculatorDE(weekCalculatorDTO, total, totalHours);
                }
                totalHours += total;
            } else
            {
                //Validamos el inicio de la hora
                if (hour >= 7 && hour < 20)
                {
                    total = convertMilitoHour(conditionalsDate(report.getStartDate(), 20), report.getStartDate());
                    calculatorNM(weekCalculatorDTO, total, totalHours);
                    totalHours += total;
                    total = 4;
                    calculatorNC(weekCalculatorDTO, total, totalHours);
                    totalHours += total;
                } else
                {
                    if (hour >= 20 && hour < 24)
                    {
                        total = convertMilitoHour(conditionalsDate(report.getStartDate(), 24), report.getStartDate());
                        calculatorNC(weekCalculatorDTO, total, totalHours);
                        totalHours += total;
                    } else if (hour >= 0 && hour < 7)
                    {
                        total = convertMilitoHour(conditionalsDate(report.getStartDate(), 7), report.getStartDate());
                        calculatorNC(weekCalculatorDTO, total, totalHours);
                        totalHours += total;
                        total = 13;
                        calculatorNM(weekCalculatorDTO, total, totalHours);
                        totalHours += total;
                        total = 4;
                        calculatorNC(weekCalculatorDTO, total, totalHours);
                        totalHours += total;
                    }
                }
                calendar.setTime(report.getStartDate());
                dayOfWeek = calendar.get(Calendar.DAY_OF_YEAR);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(report.getEndDate());
                dayOfWeek2 = calendar2.get(Calendar.DAY_OF_YEAR);
                dayOfWeek++;
                //Recorremos los dias intermedios
                while (dayOfWeek != dayOfWeek2)
                {
                    //Nocturna 1
                    total = 7;
                    calculatorNC(weekCalculatorDTO, total, totalHours);
                    totalHours += total;
                    //Normal
                    total = 13;
                    calculatorNM(weekCalculatorDTO, total, totalHours);
                    totalHours += total;
                    //Nocturna 2
                    total = 4;
                    calculatorNC(weekCalculatorDTO, total, totalHours);
                    totalHours += total;
                    dayOfWeek++;
                }
                calendar.setTime(report.getEndDate());
                int dayOfWeek3 = calendar.get(Calendar.DAY_OF_WEEK);
                //Validamos si es domingo
                if (dayOfWeek3 != 1)
                {
                    //Validamos el final de la hora
                    if (hour2 >= 7 && hour2 < 20)
                    {
                        total = 7;
                        calculatorNC(weekCalculatorDTO, total, totalHours);
                        totalHours += total;
                        total = convertMilitoHour(report.getEndDate(), conditionalsDate(report.getEndDate(), 7));
                        calculatorNM(weekCalculatorDTO, total, totalHours);
                        totalHours += total;
                    } else
                    {
                        if (hour2 >= 20 && hour2 < 24)
                        {
                            total = 7;
                            calculatorNC(weekCalculatorDTO, total, totalHours);
                            totalHours += total;
                            total = 13;
                            calculatorNM(weekCalculatorDTO, total, totalHours);
                            totalHours += total;
                            total = convertMilitoHour(report.getEndDate(), conditionalsDate(report.getEndDate(), 20));
                            calculatorNC(weekCalculatorDTO, total, totalHours);
                            totalHours += total;
                        } else if (hour2 >= 0 && hour2 < 7)
                        {
                            total = convertMilitoHour(report.getEndDate(), conditionalsDate(report.getEndDate(), 0));
                            calculatorNC(weekCalculatorDTO, total, totalHours);
                            totalHours += total;
                        }
                    }
                } else
                {
                    total = convertMilitoHour(report.getEndDate(), conditionalsDate(report.getEndDate(), 0));
                    calculatorDE(weekCalculatorDTO, total, totalHours);
                    totalHours += total;
                }
            }
        }
        return weekCalculatorDTO;
    }

    /*
        Se encarga de convertir milisengundos a horas
     */
    private double convertMilitoHour(Date date1, Date date2)
    {
        long elapsed = date1.getTime() - date2.getTime();
        elapsed = TimeUnit.MINUTES.convert(elapsed, TimeUnit.MILLISECONDS);
        return ((double) elapsed / 60);
    }

    /*
        Se encarga de la logica para las horas normales y horas normales extras
     */
    private void calculatorNM(WeekCalculatorDTO weekCalculatorDTO, double total, double totalHours)
    {
        if (totalHours >= 48)
        {
            weekCalculatorDTO.setHoursNmE(weekCalculatorDTO.getHoursNmE() + total);
        } else
        {
            if ((total + totalHours) > 48)
            {
                weekCalculatorDTO.setHoursNm(weekCalculatorDTO.getHoursNm() + (48 - totalHours));
                weekCalculatorDTO.setHoursNmE(weekCalculatorDTO.getHoursNmE() + ((total + totalHours) - 48));
            } else
            {
                weekCalculatorDTO.setHoursNm(weekCalculatorDTO.getHoursNm() + total);
            }
        }
    }

    /*
        Se encarga de la logica para las horas nocturnas y horas nocturnas extras
     */
    private void calculatorNC(WeekCalculatorDTO weekCalculatorDTO, double total, double totalHours)
    {
        if (totalHours >= 48)
        {
            weekCalculatorDTO.setHoursNcE(weekCalculatorDTO.getHoursNcE() + total);
        } else
        {
            if ((total + totalHours) > 48)
            {
                weekCalculatorDTO.setHoursNc(weekCalculatorDTO.getHoursNc() + (48 - totalHours));
                weekCalculatorDTO.setHoursNcE(weekCalculatorDTO.getHoursNcE() + ((total + totalHours) - 48));
            } else
            {
                weekCalculatorDTO.setHoursNc(weekCalculatorDTO.getHoursNc() + total);
            }
        }
    }

    /*
        Se encarga de la logica para las horas dominicales y horas dominicales extras
     */
    private void calculatorDE(WeekCalculatorDTO weekCalculatorDTO, double total, double totalHours)
    {
        if (totalHours >= 48)
        {
            weekCalculatorDTO.setHoursDe(weekCalculatorDTO.getHoursDe() + total);
        } else
        {
            if ((total + totalHours) > 48)
            {
                weekCalculatorDTO.setHoursD(weekCalculatorDTO.getHoursD() + (48 - totalHours));
                weekCalculatorDTO.setHoursDe(weekCalculatorDTO.getHoursDe() + ((total + totalHours) - 48));
            } else
            {
                weekCalculatorDTO.setHoursD(weekCalculatorDTO.getHoursD() + total);
            }
        }
    }

    /*
        Se encarga de establecer un horario especifico
     */
    private Date conditionalsDate(Date date, int hour)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
