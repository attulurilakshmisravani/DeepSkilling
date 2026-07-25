// Hands-On 6, Task 1, Step 59: shared Course interface used across the whole app
export interface Course {
  id: number;
  name: string;
  code: string;
  credits: number;
  gradeStatus: 'passed' | 'failed' | 'pending';
}
