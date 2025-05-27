import { isPlatformServer, isPlatformBrowser } from '@angular/common';
import { Component, Inject, PLATFORM_ID, OnInit } from '@angular/core';

@Component({
  selector: 'app-testimonial',
  templateUrl: './testimonial.component.html',
  styleUrls: ['./testimonial.component.css']
})
export class TESTIMONIALComponent implements OnInit {
  clientComment: string = '';
  skipHydration: boolean = false;

  testimonials = [
    {
      imageUrl: "/assets/img/testimonial-1.jpg",
      content: "Clita clita tempor justo dolor ipsum amet kasd amet duo justo duo duo labore sed sed. Magna ut diam sit et amet stet eos sed clita erat magna elitr erat sit sit erat at rebum justo sea clita.",
      clientName: "Client Name 1",
      profession: "Profession 1"
    },
    {
      imageUrl: "/assets/img/testimonial-2.jpg",
      content: "Clita clita tempor justo dolor ipsum amet kasd amet duo justo duo duo labore sed sed. Magna ut diam sit et amet stet eos sed clita erat magna elitr erat sit sit erat at rebum justo sea clita.",
      clientName: "Client Name 2",
      profession: "Profession 2"
    },
    {
      imageUrl: "/assets/img/testimonial-1.jpg",
      content: "Clita clita tempor justo dolor ipsum amet kasd amet duo justo duo duo labore sed sed. Magna ut diam sit et amet stet eos sed clita erat magna elitr erat sit sit erat at rebum justo sea clita.",
      clientName: "Client Name 2",
      profession: "Profession 2"
    }
    // Add more testimonials as needed
  ];

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit() {
    if (isPlatformServer(this.platformId)) {
      console.log('Côté serveur');
      // Logique côté serveur
    }
    if (isPlatformBrowser(this.platformId)) {
      console.log('Côté client');
      this.skipHydration = true;
      // Logique côté client
    }
  }

  submitComment() {
    // You can add the comment handling logic here
    console.log('Comment submitted:', this.clientComment);
    // Reset the comment field after submission if needed
    this.clientComment = '';
  }
}
