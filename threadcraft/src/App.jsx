import Navbar from "./components/Navbar.jsx";
import Hero from "./components/Hero.jsx";
import TshirtCustomizer from "./components/TshirtCustomizer.jsx";
import Services from "./components/Services.jsx";
import HowItWorks from "./components/HowItWorks.jsx";
import WhyChooseUs from "./components/WhyChooseUs.jsx";
import Gallery from "./components/Gallery.jsx";
import Testimonials from "./components/Testimonials.jsx";
import FAQ from "./components/FAQ.jsx";
import CTA from "./components/CTA.jsx";
import Contact from "./components/Contact.jsx";
import Footer from "./components/Footer.jsx";
import StickyMobileCTA from "./components/StickyMobileCTA.jsx";

export default function App() {
  return (
    <div className="relative bg-ink">
      <div className="grain-overlay" />
      <Navbar />
      <main className="pb-20 sm:pb-0">
        <Hero />
        <TshirtCustomizer />
        <Services />
        <HowItWorks />
        <WhyChooseUs />
        <Gallery />
        <Testimonials />
        <FAQ />
        <CTA />
        <Contact />
      </main>
      <Footer />
      <StickyMobileCTA />
    </div>
  );
}
