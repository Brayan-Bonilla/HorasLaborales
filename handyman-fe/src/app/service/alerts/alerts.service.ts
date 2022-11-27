import { Injectable } from '@angular/core';
import {ToastrService} from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AlertsService {

  constructor(private toast:ToastrService) { }

  showSuccess(text,tittle){
    this.toast.success(text,tittle);
  }

  showError(text,tittle){
    this.toast.error(text,tittle);
  }

  showInfo(text,tittle){
    this.toast.info(text,tittle);
  }
}
