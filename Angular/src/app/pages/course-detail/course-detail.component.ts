// Hands-On 7, Task 1, Step 69: reads the :id route parameter and loads the matching course
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { CourseService } from '../../services/course.service';
import { EnrollmentService } from '../../services/enrollment.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-course-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './course-detail.component.html',
  styleUrl: './course-detail.component.css',
})
export class CourseDetailComponent implements OnInit {
  course?: Course;
  // Hands-On 8, Task 2, Step 87: switchMap cancels the previous inner Observable
  // (the students lookup for the last selected course) if a new courseId arrives first.
  students$?: Observable<{ id: number; name: string }[]>;

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.courseService.getCourseById(id).subscribe((course) => (this.course = course));

    this.students$ = this.route.paramMap.pipe(
      switchMap((params) => this.enrollmentService.getStudentsByCourse(Number(params.get('id'))))
    );
  }
}
