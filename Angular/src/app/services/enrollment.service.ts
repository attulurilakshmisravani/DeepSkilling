// Hands-On 6, Task 2: EnrollmentService demonstrates service-to-service injection
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Course } from '../models/course.model';
import { CourseService } from './course.service';

@Injectable({ providedIn: 'root' })
export class EnrollmentService {
  private enrolledCourseIds: number[] = [];

  constructor(private courseService: CourseService) {}

  enroll(courseId: number): void {
    if (!this.enrolledCourseIds.includes(courseId)) {
      this.enrolledCourseIds.push(courseId);
    }
  }

  unenroll(courseId: number): void {
    this.enrolledCourseIds = this.enrolledCourseIds.filter((id) => id !== courseId);
  }

  isEnrolled(courseId: number): boolean {
    return this.enrolledCourseIds.includes(courseId);
  }

  getEnrolledCourses(): Observable<Course[]> {
    return this.courseService
      .getCourses()
      .pipe(map((courses) => courses.filter((c) => this.enrolledCourseIds.includes(c.id))));
  }

  // Hands-On 8, Task 2, Step 87: used with switchMap when a course is selected
  getStudentsByCourse(courseId: number): Observable<{ id: number; name: string }[]> {
    return this.courseService.getCourseById(courseId).pipe(
      map(() => [{ id: 1, name: 'Sample Student' }]) // placeholder until a real students endpoint exists
    );
  }
}
