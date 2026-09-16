package com.example.threadcraft.data

import androidx.compose.ui.graphics.Color

data class ServiceItem(
    val id: String,
    val tag: String,
    val title: String,
    val description: String,
    val iconName: String
)

val servicesList = listOf(
    ServiceItem(
        id = "custom",
        tag = "01",
        title = "Custom T-Shirts",
        description = "One-off tees for personal projects, gifts, or a design you just need to see printed.",
        iconName = "Shirt"
    ),
    ServiceItem(
        id = "college",
        tag = "02",
        title = "College & Events",
        description = "Batch tees, fests, and farewell drops — coordinated sizing and fast turnaround for large groups.",
        iconName = "GraduationCap"
    ),
    ServiceItem(
        id = "corporate",
        tag = "03",
        title = "Corporate Wear",
        description = "On-brand uniforms and merch that hold up to daily wear without fading or cracking.",
        iconName = "Briefcase"
    ),
    ServiceItem(
        id = "sports",
        tag = "04",
        title = "Sports Jerseys",
        description = "Numbered, named, and printed for stretch fabric — built to survive the season.",
        iconName = "Trophy"
    ),
    ServiceItem(
        id = "bulk",
        tag = "05",
        title = "Bulk Printing",
        description = "500 pieces or 5,000 — the same quality control on every unit, priced to scale down.",
        iconName = "Boxes"
    ),
    ServiceItem(
        id = "design",
        tag = "06",
        title = "Custom Designs",
        description = "No artwork yet? Our design team builds print-ready art from a rough idea or reference.",
        iconName = "PenTool"
    )
)

data class StepItem(
    val number: String,
    val title: String,
    val description: String
)

val howItWorksSteps = listOf(
    StepItem("01", "Choose Your T-Shirt", "Pick a fit, fabric, and colour as your starting canvas."),
    StepItem("02", "Upload Your Design", "Drop in artwork, a logo, or a photo — or sketch it with us."),
    StepItem("03", "We Print It", "Screen or DTF printed in-house and checked piece by piece."),
    StepItem("04", "You Wear It", "Delivered folded and ready, from a single tee to a full batch.")
)

data class WhyChooseUsStat(
    val value: Int,
    val suffix: String,
    val label: String,
    val description: String
)

val whyChooseUsStats = listOf(
    WhyChooseUsStat(4, "×", "Wash-tested prints", "Colour holds through repeated washes without cracking."),
    WhyChooseUsStat(100, "%", "Premium fabric", "Combed cotton and blends chosen for print and drape."),
    WhyChooseUsStat(72, "hr", "Fast turnaround", "Standard orders leave the shop within three days."),
    WhyChooseUsStat(6, "", "Print methods", "Screen, DTF, embroidery, vinyl, sublimation, and puff print.")
)

data class GalleryItem(
    val id: String,
    val category: String,
    val title: String,
    val fromColor: Color,
    val toColor: Color,
    val accentColor: Color
)

val galleryItems = listOf(
    GalleryItem("street-1", "Streetwear", "Oversized Drop", Color(0xFF1C1D22), Color(0xFF3A1614), Color(0xFFD6301F)),
    GalleryItem("college-1", "College", "Fest Batch '26", Color(0xFF1B1F2B), Color(0xFF2C2413), Color(0xFFC6A24D)),
    GalleryItem("corporate-1", "Corporate", "Onboarding Kit", Color(0xFF14161C), Color(0xFF242832), Color(0xFFEDE8DD)),
    GalleryItem("sports-1", "Sports", "League Jersey", Color(0xFF1A1F18), Color(0xFF0F2A1C), Color(0xFF4E9A6B)),
    GalleryItem("events-1", "Events", "Launch Party Tee", Color(0xFF221420), Color(0xFF3A1533), Color(0xFFB85BC9)),
    GalleryItem("street-2", "Streetwear", "Minimal Mark", Color(0xFF1C1D22), Color(0xFF2A1C1A), Color(0xFFD6301F))
)

data class TestimonialItem(
    val name: String,
    val role: String,
    val quote: String
)

val testimonials = listOf(
    TestimonialItem(
        name = "Aditya R.",
        role = "College fest coordinator",
        quote = "Ordered 180 event tees with two days' notice. Every shirt matched the mockup, sizing was accurate across the whole batch."
    ),
    TestimonialItem(
        name = "Priya N.",
        role = "Startup founder",
        quote = "We reorder our team merch here every quarter. Same quality every time, and they just get it right without back-and-forth."
    ),
    TestimonialItem(
        name = "Coach Manoj",
        role = "Local football club",
        quote = "Jerseys held up through a full season of matches and washes without the print cracking or the numbers peeling."
    )
)

data class FaqItem(
    val question: String,
    val answer: String
)

val faqs = listOf(
    FaqItem(
        question = "What's the minimum order size?",
        answer = "There isn't one. We print single tees for personal projects and orders of several thousand for corporate or event runs, with the same quality checks either way."
    ),
    FaqItem(
        question = "How long does printing take?",
        answer = "Standard orders are ready within 72 hours. Larger bulk runs are scheduled around your event date — tell us your deadline and we'll confirm it's workable before you order."
    ),
    FaqItem(
        question = "Can I see a sample before the full order?",
        answer = "Yes. For bulk and corporate orders we print a sample piece first so you can approve colour, fit, and print quality before the full batch runs."
    ),
    FaqItem(
        question = "Which print methods do you use?",
        answer = "Screen printing, DTF, embroidery, vinyl, sublimation, and puff print. We'll recommend the right one for your design and fabric when you send it over."
    ),
    FaqItem(
        question = "Do you deliver outside Vizianagaram?",
        answer = "Yes, we ship across India. Delivery timelines depend on order size and destination — ask us for an estimate with your quote."
    )
)
