// Hands-On 9, Task 2, Step 99
import { createReducer, on } from '@ngrx/store';
import { enrollInCourse, setEnrolledCourses, unenrollFromCourse } from './enrollment.actions';

export interface EnrollmentState {
  enrolledCourseIds: number[];
}

export const initialEnrollmentState: EnrollmentState = {
  enrolledCourseIds: [],
};

export const enrollmentReducer = createReducer(
  initialEnrollmentState,
  on(enrollInCourse, (state, { courseId }) => ({
    enrolledCourseIds: state.enrolledCourseIds.includes(courseId)
      ? state.enrolledCourseIds
      : [...state.enrolledCourseIds, courseId],
  })),
  on(unenrollFromCourse, (state, { courseId }) => ({
    enrolledCourseIds: state.enrolledCourseIds.filter((id) => id !== courseId),
  })),
  on(setEnrolledCourses, (_state, { courseIds }) => ({ enrolledCourseIds: courseIds }))
);
