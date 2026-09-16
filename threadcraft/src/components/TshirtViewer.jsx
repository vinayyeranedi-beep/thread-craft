import { Suspense, useMemo, useState, Component } from "react";
import { Canvas } from "@react-three/fiber";
import { ContactShadows, Environment, Lightformer, OrbitControls } from "@react-three/drei";
import { Shirt } from "lucide-react";
import TshirtModel from "./TshirtModel.jsx";

function supportsWebGL() {
  try {
    const canvas = document.createElement("canvas");
    return !!(
      window.WebGLRenderingContext &&
      (canvas.getContext("webgl") || canvas.getContext("experimental-webgl"))
    );
  } catch {
    return false;
  }
}

class ViewerErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }
  static getDerivedStateFromError() {
    return { hasError: true };
  }
  componentDidCatch(error) {
    // eslint-disable-next-line no-console
    console.warn("3D viewer failed, showing fallback:", error);
  }
  render() {
    if (this.state.hasError) return this.props.fallback;
    return this.props.children;
  }
}

function StaticFallback({ color = "#111114" }) {
  return (
    <div className="flex h-full w-full flex-col items-center justify-center gap-4 rounded-[28px] border border-ink-line bg-gradient-to-b from-ink-soft to-ink text-paper-dim">
      <Shirt size={72} color={color === "#111114" ? "#EDE8DD" : color} strokeWidth={1.25} />
      <p className="max-w-[220px] text-center text-sm">
        3D preview isn't available in this browser. Your design will still print exactly as configured.
      </p>
    </div>
  );
}

function Loader() {
  return (
    <div className="flex h-full w-full items-center justify-center">
      <div className="h-10 w-10 animate-spin rounded-full border-2 border-thread border-t-transparent" />
    </div>
  );
}

export default function TshirtViewer({
  color = "#111114",
  view = "front",
  designId = "none",
  uploadedImageUrl = null,
  autoRotate = true,
  enableZoom = false,
  className = "",
}) {
  const [webglOk] = useState(() => (typeof window !== "undefined" ? supportsWebGL() : true));
  const dpr = useMemo(() => (typeof window !== "undefined" ? Math.min(window.devicePixelRatio, 2) : 1), []);

  if (!webglOk) {
    return (
      <div className={className}>
        <StaticFallback color={color} />
      </div>
    );
  }

  return (
    <div className={className}>
      <ViewerErrorBoundary fallback={<StaticFallback color={color} />}>
        <Suspense fallback={<Loader />}>
          <Canvas
            dpr={dpr}
            shadows
            camera={{ position: [0, 0.15, 4.2], fov: 32 }}
            gl={{ antialias: true, alpha: true, powerPreference: "high-performance" }}
          >
            <ambientLight intensity={0.35} />
            <directionalLight
              position={[3, 4, 4]}
              intensity={1.4}
              castShadow
              shadow-mapSize-width={1024}
              shadow-mapSize-height={1024}
            />
            <directionalLight position={[-4, 2, -3]} intensity={0.5} color="#C6A24D" />
            <spotLight position={[0, 5, -2]} intensity={0.6} color="#D6301F" angle={0.5} penumbra={1} />

            <TshirtModel color={color} view={view} designId={designId} uploadedImageUrl={uploadedImageUrl} />

            <ContactShadows position={[0, -1.45, 0]} opacity={0.55} scale={6} blur={2.4} far={2} />

            {/* Procedural studio environment — built from light panels rendered
                to a cubemap in-scene, so reflections work with zero external
                HDR fetches (no network dependency, nothing to fail offline). */}
            <Environment resolution={256}>
              <group>
                <Lightformer form="rect" intensity={2.2} color="#F5F1E8" position={[0, 4, 2]} scale={[6, 3, 1]} />
                <Lightformer form="rect" intensity={1.1} color="#EDE8DD" position={[-4, 1, 3]} rotation={[0, Math.PI / 3, 0]} scale={[4, 3, 1]} />
                <Lightformer form="rect" intensity={0.9} color="#C6A24D" position={[4, 0.5, -2]} rotation={[0, -Math.PI / 3, 0]} scale={[4, 3, 1]} />
                <Lightformer form="ring" intensity={1.4} color="#D6301F" position={[0, -3, -3]} scale={3} />
              </group>
            </Environment>

            <OrbitControls
              enableZoom={enableZoom}
              enablePan={false}
              minPolarAngle={Math.PI / 2.8}
              maxPolarAngle={Math.PI / 1.7}
              autoRotate={autoRotate}
              autoRotateSpeed={1.1}
              rotateSpeed={0.6}
              enableDamping
              dampingFactor={0.08}
            />
          </Canvas>
        </Suspense>
      </ViewerErrorBoundary>
    </div>
  );
}
