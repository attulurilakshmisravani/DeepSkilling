// Hands-On 10, Task 2: service testing with HttpClientTestingModule
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CourseService } from './course.service';
import { Course } from '../models/course.model';

describe('CourseService', () => {
  let service: CourseService;
  let httpMock: HttpTestingController;

  const mockCourses: Course[] = [
    { id: 1, name: 'Data Structures', code: 'CS101', credits: 4, gradeStatus: 'passed' },
    { id: 2, name: 'Operating Systems', code: 'CS102', credits: 3, gradeStatus: 'pending' },
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CourseService],
    });
    service = TestBed.inject(CourseService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // asserts no unexpected/outstanding HTTP requests were made
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch courses via GET', () => {
    service.getCourses().subscribe((courses) => {
      expect(courses.length).toBe(2);
    });

    const req = httpMock.expectOne('http://localhost:3000/courses');
    expect(req.request.method).toBe('GET');
    req.flush(mockCourses);
  });

  it('should propagate an error message on a failed request', () => {
    service.getCourses().subscribe({
      next: () => fail('expected an error, not a successful response'),
      error: (err) => expect(err.message).toContain('Failed to load courses'),
    });

    // retry(2) means the service issues the original request plus 2 retries = 3 total
    for (let i = 0; i < 3; i++) {
      httpMock.expectOne('http://localhost:3000/courses').flush('server error', {
        status: 500,
        statusText: 'Internal Server Error',
      });
    }
  });
});
