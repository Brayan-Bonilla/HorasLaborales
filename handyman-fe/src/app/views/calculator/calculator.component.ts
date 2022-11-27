import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../service/api/api.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Calculator } from '../../models/calculator.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent implements OnInit {

  calculatorHourR!: Calculator;

  constructor(private api: ApiService, private router: Router) { }

  reportService = new FormGroup({
    idTechnical: new FormControl('', Validators.required),
    week: new FormControl('', Validators.required)
  })

  ngOnInit(): void {

  }

  weekCalculator(form) {
    this.api.calculatorHour(form.idTechnical, form.week).subscribe(data => {
      this.calculatorHourR = data;
    });
  }

  redirectPage(){
    this.router.navigate(['hour']);
  }
}
