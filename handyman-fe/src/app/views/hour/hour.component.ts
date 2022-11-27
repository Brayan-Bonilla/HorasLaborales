import { Component, OnInit } from '@angular/core';

import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ApiService } from '../../service/api/api.service';
import { ReportI } from '../../models/report.interface';
import { ReportResponse } from '../../models/report.response.interface';
import { Router } from '@angular/router';
import { AlertsService } from '../../service/alerts/alerts.service';

@Component({
  selector: 'app-hour',
  templateUrl: './hour.component.html',
  styleUrls: ['./hour.component.css']
})
export class HourComponent implements OnInit {

  reportService = new FormGroup({
    idTechnical: new FormControl('', Validators.required),
    idService: new FormControl('', Validators.required),
    startDate: new FormControl('', Validators.required),
    endDate: new FormControl('', Validators.required)
  })

  constructor(private api: ApiService, private router: Router, private alerts: AlertsService) { }

  dataResponse!: ReportResponse;
  ngOnInit(): void {
  }

  saveReport(form) {
    let dataResponse2!: ReportResponse;
    this.dataResponse = dataResponse2;
    const date = new Date(form.startDate);
    form.startDate = date;
    const date2 = new Date(form.endDate);
    form.endDate = date2;
    if (form.idTechnical !== "" && form.idService !== "" && form.startDate !== "Invalid Date" && form.endDate !== "Invalid Date") {
      this.api.saveReport(form).subscribe(data => {
        this.dataResponse = data;
        this.alerts.showSuccess('Registro guardado correctamente', 'Hecho');
      });
    } else {
      if (this.dataResponse === undefined) {
        this.alerts.showInfo('Complete todos los campos', 'Info');
      }
    }
  }
  redirectPage() {
    this.router.navigate(['calculator']);
  }

}

