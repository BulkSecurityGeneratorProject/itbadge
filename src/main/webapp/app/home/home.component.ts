import { Component, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

import { Principal, Account } from 'app/core';
import { Router } from '@angular/router';

import { ICours } from '../shared/model/cours.model';
import { IUtilisateur } from '../shared/model/utilisateur.model';
import { IBadgeage } from '../shared/model/badgeage.model';

import { CoursService } from '../entities/cours/cours.service';
import { BadgeageService } from '../entities/badgeage/badgeage.service';
import { UtilisateurService } from '../entities/utilisateur/utilisateur.service';

import moment = require('moment');

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account = null;
    listCurrentEleve: IUtilisateur[] = [];
    cours: ICours = null;
    listBadgeage: IBadgeage[] = [];

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private router: Router,
        private coursService: CoursService,
        private badgeageService: BadgeageService,
        private utilisateurService: UtilisateurService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;

            if (this.account.authorities.includes('ROLE_ADMIN')) {
            } else if (this.account.authorities.includes('ROLE_PROFESSEUR')) {
                this.coursService.getCurrentCours().subscribe(res => {
                    this.cours = res.body;
                    let dateCours = this.cours.dateDebut.format('YYYY-MM-DD');
                    for (groupe in this.cours.listGroupes) {
                        this.groupeService.findBadgeageGroupe(groupe.id, dateCours).subscribe(res => {
                            this.listCurrentEleve.push(res[0].listEleves);
                            console.log(this.listCurrentEleve);
                        });
                    }
                });
            } else if (this.account.authorities.includes('ROLE_USER')) {
                this.coursService.getCurrentCours().subscribe(res => {
                    this.cours = res.body;
                });

                this.badgeageService.getListTodayBadgeageForStudent().subscribe(res => {
                    this.listBadgeage = res.body;
                });
            }
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isEleve() {
        if (this.account !== null) {
            return !this.account.authorities.includes('ROLE_PROFESSEUR') && !this.account.authorities.includes('ROLE_ADMIN');
        }
        return false;
    }

    isProfesseur() {
        if (this.account !== null) {
            return this.account.authorities.includes('ROLE_PROFESSEUR');
        }
        return false;
    }

    login() {
        this.router.navigate(['login']);
    }

    badger() {
        this.badgeageService.badger().subscribe(res => {
            this.badgeageService.getListTodayBadgeageForStudent().subscribe(res2 => {
                this.listBadgeage = res2.body;
            });
        });
    }
}
