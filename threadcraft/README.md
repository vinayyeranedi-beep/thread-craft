# THREADCRAFT — 3D T-Shirt Printing Website (Demo)

An interactive 3D product-demo site built for a client presentation. Not
the final brand — every business detail below is a clearly-marked
placeholder.

## ⚠️ Before anything else: how NOT to open this

**Do not double-click `index.html`.** It will load as a blank white page.
This isn't a bug — browsers block the JavaScript modules this site is
built from when they're opened directly from disk (a `file://` address).
You'll know this has happened if your browser's address bar starts with
`file:///` or `File |` instead of `http://localhost`.

## Run it

**Windows:** double-click `start-windows.bat`.
**Mac/Linux:** double-click `start-mac-linux.command` (first time only,
you may need to right-click → Open, or run `chmod +x start-mac-linux.command`
once in Terminal).

Either one installs what's needed and opens the site in your browser
automatically. Requires [Node.js](https://nodejs.org) to be installed first.

Prefer the command line? Same result:

```bash
npm install
npm run dev
```

This opens your browser automatically at the correct address (usually
`http://localhost:5173`). If it doesn't open on its own, copy that URL
from the terminal into your browser — never open the file itself.

To produce a production build:

```bash
npm run build
npm run preview
```

`npm run preview` also serves it correctly over `http://localhost` — the
`dist` folder it builds has the same file:// restriction, so it needs a
real deploy (see Deploying below) or `npm run preview` to view locally.

## Where to change things

| What | File |
|---|---|
| Business name, tagline, hero copy | `src/config/business.js` |
| WhatsApp number | `src/config/business.js` → `WHATSAPP_NUMBER` |
| Phone / Instagram / location | `src/config/business.js` |
| Logo | `src/config/business.js` → `logo` (path to an image in `public/images/`) |
| Services / product cards | `src/data/services.js` |
| Gallery items | `src/data/services.js` → `galleryItems` |
| Shirt colour options | `src/data/products.js` |

## Replacing the 3D T-shirt with a real model

The demo ships with a procedurally generated stand-in shirt (built in
`src/utils/shirtGeometry.js`) so it runs with zero external assets. To use
a real GLB:

1. Export your model as `tshirt.glb` and place it at `public/models/tshirt.glb`.
2. Open `src/components/TshirtModel.jsx` and set `USE_GLB_MODEL = true`.
3. If the mesh you want recoloured isn't named `"Shirt"` in your GLB,
   update the check inside `GltfShirt()` in the same file.

Everything else — colour switching, front/back rotation, the design
decal, drag-to-rotate — keeps working against the new model.

## Deploying

This is a static Vite build — any static host works:

- **Vercel / Netlify**: point the project at this repo, build command
  `npm run build`, output directory `dist`.
- **Manual**: run `npm run build`, then upload the contents of `dist/` to
  any static file host.

## Notes for the client presentation

- The "INTERACTIVE 3D DEMO" badge in the footer, and the "demo" label
  next to the logo in the navbar, are meant to be removed before launch —
  they exist so this is clearly presented as a prototype.
- Contact details in the Contact section are demo placeholders and are
  labelled as such on the page.
- The image upload in "MAKE IT YOURS" is a live in-browser preview, not a
  production print-file pipeline.
