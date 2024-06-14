import { Component, OnInit } from '@angular/core';
import { ApiService } from './api.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

interface ListItem {
  id: number;
  title: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'angular-list';
  myForm!: FormGroup;
  apiData: ListItem[] = [];
  list: ListItem[] = [];
  error: any;
  deletionSuccess$ = new Subject<boolean>();
  constructor(
    private apiService: ApiService,
    private fb: FormBuilder,
    private http: HttpClient
  ) {
    this.myForm = this.fb.group({
      title: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.apiService.getApiData().subscribe(
      (data) => {
        this.apiData = data;
      },
      (error) => {
        this.error = error;
      }
    );
    this.handleDeletionSuccess();
  }

  onSubmit() {
    if (this.myForm.valid) {
      const formData = this.myForm.value;
      this.http.post('http://localhost:8080/api/list', formData).subscribe(
        (response) => {
          console.log('Data submitted successfully!', response);
          this.apiService.getApiData().subscribe(
            (data) => {
              this.apiData = data;
            },
            (error) => {
              this.error = error;
            }
          );
          this.myForm.reset();
        },
        (error) => {
          console.error('Error submitting data!', error);
        }
      );
    }
  }

  deleteItem(id: number) {
    this.apiService.deleteItemById(id).subscribe(
      (response) => {
        if (response && response?.success === true) { 
          console.log('Item deleted successfully!', response);
          this.list = this.apiData.filter(item => item?.id !== id);
          this.deletionSuccess$.next(true);
        }
      },
      (error) => {
        console.error('Error deleting item!', error);
      }
    );
  }

  handleDeletionSuccess() {
    this.deletionSuccess$.subscribe((success) => {
      if (success) {
        console.log('Item deletion successful! (from observable)');
        this.apiService.getApiData().subscribe(
          (data) => {
            this.apiData = data;
          },
          (error) => {
            this.error = error;
          }
        );
      }
    });
  }
}
