import { Injectable } from '@angular/core';
import { ReportI } from '../../models/report.interface';
import { ReportResponse } from '../../models/report.response.interface';
import { Calculator } from '../../models/calculator.interface';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  url: string = "http://localhost:8080/HandyMan/api/"
  constructor(private http: HttpClient) { }

  saveReport(form: ReportI): Observable<ReportResponse> {
    let path = this.url + "saveReport";
    return this.http.post<ReportResponse>(path, form)
  }

  calculatorHour(idTechnical: string, week: number): Observable<Calculator>{
    let path = this.url + "calculate/idTechnical/" + idTechnical + "/week/" + week;
    return this.http.get<Calculator>(path);
  }
}
