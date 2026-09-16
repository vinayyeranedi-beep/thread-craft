import { lazy, Suspense } from "react";

// Three.js + @react-three/fiber/drei are the heaviest dependencies in this
// project. Loading TshirtViewer lazily keeps them out of the initial JS
// bundle so the hero's copy, nav, and layout paint immediately, with the
// 3D scene streaming in right behind it.
const TshirtViewer = lazy(() => import("./TshirtViewer.jsx"));

function ViewerSkeleton({ className }) {
  return (
    <div className={className}>
      <div className="flex h-full w-full items-center justify-center rounded-[28px] border border-ink-line bg-ink-soft">
        <div className="h-10 w-10 animate-spin rounded-full border-2 border-thread border-t-transparent" />
      </div>
    </div>
  );
}

export default function LazyTshirtViewer(props) {
  return (
    <Suspense fallback={<ViewerSkeleton className={props.className} />}>
      <TshirtViewer {...props} />
    </Suspense>
  );
}
