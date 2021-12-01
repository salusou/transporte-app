jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CompaniesXUsersService } from '../service/companies-x-users.service';
import { ICompaniesXUsers, CompaniesXUsers } from '../companies-x-users.model';
import { ICompanies } from 'app/entities/companies/companies.model';
import { CompaniesService } from 'app/entities/companies/service/companies.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { CompaniesXUsersUpdateComponent } from './companies-x-users-update.component';

describe('Component Tests', () => {
  describe('CompaniesXUsers Management Update Component', () => {
    let comp: CompaniesXUsersUpdateComponent;
    let fixture: ComponentFixture<CompaniesXUsersUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let companiesXUsersService: CompaniesXUsersService;
    let companiesService: CompaniesService;
    let userService: UserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CompaniesXUsersUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CompaniesXUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompaniesXUsersUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      companiesXUsersService = TestBed.inject(CompaniesXUsersService);
      companiesService = TestBed.inject(CompaniesService);
      userService = TestBed.inject(UserService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Companies query and add missing value', () => {
        const companiesXUsers: ICompaniesXUsers = { id: 456 };
        const companies: ICompanies = { id: 14504 };
        companiesXUsers.companies = companies;

        const companiesCollection: ICompanies[] = [{ id: 16474 }];
        jest.spyOn(companiesService, 'query').mockReturnValue(of(new HttpResponse({ body: companiesCollection })));
        const additionalCompanies = [companies];
        const expectedCollection: ICompanies[] = [...additionalCompanies, ...companiesCollection];
        jest.spyOn(companiesService, 'addCompaniesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ companiesXUsers });
        comp.ngOnInit();

        expect(companiesService.query).toHaveBeenCalled();
        expect(companiesService.addCompaniesToCollectionIfMissing).toHaveBeenCalledWith(companiesCollection, ...additionalCompanies);
        expect(comp.companiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call User query and add missing value', () => {
        const companiesXUsers: ICompaniesXUsers = { id: 456 };
        const user: IUser = { id: 14245 };
        companiesXUsers.user = user;

        const userCollection: IUser[] = [{ id: 81626 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ companiesXUsers });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const companiesXUsers: ICompaniesXUsers = { id: 456 };
        const companies: ICompanies = { id: 11149 };
        companiesXUsers.companies = companies;
        const user: IUser = { id: 57209 };
        companiesXUsers.user = user;

        activatedRoute.data = of({ companiesXUsers });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(companiesXUsers));
        expect(comp.companiesSharedCollection).toContain(companies);
        expect(comp.usersSharedCollection).toContain(user);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CompaniesXUsers>>();
        const companiesXUsers = { id: 123 };
        jest.spyOn(companiesXUsersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ companiesXUsers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: companiesXUsers }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(companiesXUsersService.update).toHaveBeenCalledWith(companiesXUsers);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CompaniesXUsers>>();
        const companiesXUsers = new CompaniesXUsers();
        jest.spyOn(companiesXUsersService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ companiesXUsers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: companiesXUsers }));
        saveSubject.complete();

        // THEN
        expect(companiesXUsersService.create).toHaveBeenCalledWith(companiesXUsers);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CompaniesXUsers>>();
        const companiesXUsers = { id: 123 };
        jest.spyOn(companiesXUsersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ companiesXUsers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(companiesXUsersService.update).toHaveBeenCalledWith(companiesXUsers);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCompaniesById', () => {
        it('Should return tracked Companies primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCompaniesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
