import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HourComponent} from './views/hour/hour.component';
import {CalculatorComponent} from './views/calculator/calculator.component';

const routes: Routes = [
  {path:'', redirectTo:'hour', pathMatch:'full'},
  {path:'hour', component: HourComponent},
  {path:'calculator', component: CalculatorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [HourComponent, CalculatorComponent]
